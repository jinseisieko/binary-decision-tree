# ArrayDecisionTree Design Specification

## Overview

`ArrayDecisionTree` is an array-backed implementation of `BinaryDecisionTree` using heap-style indexing for O(1) navigation operations.

## Design Goals

1. **Performance**: O(1) parent/sibling navigation
2. **Memory Efficiency**: Reduced overhead vs pointer-based trees
3. **Cache Locality**: Contiguous memory access patterns
4. **Fixed Structure**: Known maximum depth at creation

## Data Structure

### Array Indexing Scheme

```
Heap-style binary tree indexing:

                    [0] root
                   /     \
                [1]       [2]
               /   \      /   \
            [3]     [4] [5]   [6]
           /  \    /  \
        [7]  [8] [9] [10]

Index relationships:
├── Root:              0
├── Left child (true): 2*i + 1
├── Right child (false): 2*i + 2
└── Parent:            (i - 1) / 2  (integer division)
```

### Storage Arrays

```java
public class ArrayDecisionTree<I, O> implements BinaryDecisionTree<I, O> {
    // Node type at each index
    private enum NodeType { EMPTY, CONDITION, OUTCOME }
    
    private final int maxDepth;
    private final int arraySize;  // 2^(maxDepth+1) - 1
    
    private NodeType[] nodeTypes;      // Type of each node
    private Predicate<I>[] conditions; // Conditions (for CONDITION nodes)
    private Function<I, O>[] outcomes; // Outcomes (for OUTCOME nodes)
    
    private int actualDepth = -1;      // Calculated after build
    private boolean fullyDefined = false;
    private boolean built = false;
    
    // Builder state
    private int currentIndex = 0;
}
```

### Memory Layout

```
For maxDepth = 10:
├── arraySize = 2^11 - 1 = 2047 elements
├── nodeTypes:    2047 × 4 bytes = 8,188 bytes
├── conditions:   2047 × 4 bytes = 8,188 bytes
├── outcomes:     2047 × 4 bytes = 8,188 bytes
└── Total:        ~24 KB (fixed)

Compare to DynamicDecisionTree:
├── 2047 nodes × 32 bytes = 65 KB
└── ArrayDecisionTree saves ~62% memory!
```

## Implementation

### Constructors

```java
/**
 * Creates empty tree with specified maximum depth.
 */
public ArrayDecisionTree(int maxDepth) {
    if (maxDepth < 0) {
        throw new IllegalArgumentException("maxDepth must be non-negative");
    }
    this.maxDepth = maxDepth;
    this.arraySize = (1 << (maxDepth + 1)) - 1;  // 2^(maxDepth+1) - 1
    this.nodeTypes = new NodeType[arraySize];
    this.conditions = (Predicate<I>[]) new Predicate[arraySize];
    this.outcomes = (Function<I, O>[]) new Function[arraySize];
    
    // Initialize all as EMPTY
    Arrays.fill(nodeTypes, NodeType.EMPTY);
}

/**
 * Creates tree from existing branch.
 */
public ArrayDecisionTree(int maxDepth, DecisionBranch<I, O> branch) {
    this(maxDepth);
    // Copy branch structure into arrays
    copyBranch(branch, 0, 0);
}

private void copyBranch(DecisionBranch<I, O> branch, int index, int depth) {
    // Implementation for branch import
}
```

### Core Operations

#### `decide(I input)`

```java
@Override
public O decide(I input) {
    if (!built) {
        throw new IllegalStateException("Tree must be built before deciding");
    }
    Objects.requireNonNull(input, "Input cannot be null");
    
    int index = 0;
    while (index < arraySize) {
        switch (nodeTypes[index]) {
            case OUTCOME:
                return outcomes[index].apply(input);
            case CONDITION:
                if (conditions[index].test(input)) {
                    index = 2 * index + 1;  // Go to true child
                } else {
                    index = 2 * index + 2;  // Go to false child
                }
                break;
            case EMPTY:
                throw new IllegalStateException("Reached empty node at index " + index);
        }
    }
    throw new IllegalStateException("Index out of bounds");
}
```

#### Navigation Methods

```java
@Override
public DecisionTreeBuilder<I, O> goToTrueChild() {
    checkBuilt();
    if (nodeTypes[currentIndex] != NodeType.CONDITION) {
        throw new IllegalStateException("Current node is not a condition");
    }
    int childIndex = 2 * currentIndex + 1;
    if (childIndex >= arraySize) {
        throw new IllegalStateException("True child index out of bounds");
    }
    if (nodeTypes[childIndex] == NodeType.EMPTY) {
        // Auto-create placeholder outcome
        outcomes[childIndex] = input -> {
            throw new IllegalStateException("Reached unbuilt node");
        };
        nodeTypes[childIndex] = NodeType.OUTCOME;
    }
    currentIndex = childIndex;
    return this;
}

@Override
public DecisionTreeBuilder<I, O> goToFalseChild() {
    checkBuilt();
    if (nodeTypes[currentIndex] != NodeType.CONDITION) {
        throw new IllegalStateException("Current node is not a condition");
    }
    int childIndex = 2 * currentIndex + 2;
    if (childIndex >= arraySize) {
        throw new IllegalStateException("False child index out of bounds");
    }
    if (nodeTypes[childIndex] == NodeType.EMPTY) {
        outcomes[childIndex] = input -> {
            throw new IllegalStateException("Reached unbuilt node");
        };
        nodeTypes[childIndex] = NodeType.OUTCOME;
    }
    currentIndex = childIndex;
    return this;
}

@Override
public DecisionTreeBuilder<I, O> goToParent() {
    checkBuilt();
    if (currentIndex == 0) {
        throw new IllegalStateException("Already at root");
    }
    currentIndex = (currentIndex - 1) / 2;  // O(1)!
    return this;
}

@Override
public DecisionTreeBuilder<I, O> goToSibling() {
    checkBuilt();
    if (currentIndex == 0) {
        throw new IllegalStateException("Root has no sibling");
    }
    // XOR trick: if even (false child), sibling is index-1; if odd (true child), sibling is index+1
    currentIndex = (currentIndex % 2 == 0) ? currentIndex - 1 : currentIndex + 1;
    if (nodeTypes[currentIndex] == NodeType.EMPTY) {
        outcomes[currentIndex] = input -> {
            throw new IllegalStateException("Reached unbuilt node");
        };
        nodeTypes[currentIndex] = NodeType.OUTCOME;
    }
    return this;
}

@Override
public DecisionTreeBuilder<I, O> goToRoot() {
    currentIndex = 0;
    return this;
}
```

#### Modification Methods

```java
@Override
public DecisionTreeBuilder<I, O> insertCondition(Predicate<I> condition) {
    Objects.requireNonNull(condition, "Condition cannot be null");
    nodeTypes[currentIndex] = NodeType.CONDITION;
    conditions[currentIndex] = condition;
    outcomes[currentIndex] = null;
    return this;
}

@Override
public DecisionTreeBuilder<I, O> insertOutcome(Function<I, O> handler) {
    Objects.requireNonNull(handler, "Handler cannot be null");
    nodeTypes[currentIndex] = NodeType.OUTCOME;
    outcomes[currentIndex] = handler;
    conditions[currentIndex] = null;
    return this;
}

@Override
public DecisionTreeBuilder<I, O> insertOutcome(O value) {
    Objects.requireNonNull(value, "Value cannot be null");
    nodeTypes[currentIndex] = NodeType.OUTCOME;
    outcomes[currentIndex] = input -> value;
    conditions[currentIndex] = null;
    return this;
}

@Override
public DecisionTreeBuilder<I, O> clearSubtree() {
    if (nodeTypes[currentIndex] == NodeType.CONDITION) {
        clearSubtreeRecursive(currentIndex);
    }
    return this;
}

private void clearSubtreeRecursive(int index) {
    if (index >= arraySize) return;
    
    nodeTypes[index] = NodeType.EMPTY;
    conditions[index] = null;
    outcomes[index] = null;
    
    // Clear children
    clearSubtreeRecursive(2 * index + 1);
    clearSubtreeRecursive(2 * index + 2);
}
```

#### Query Methods

```java
@Override
public int getDepth() {
    if (!built) {
        throw new IllegalStateException("Tree must be built");
    }
    return actualDepth;
}

@Override
public boolean isFullyDefined() {
    if (!built) {
        throw new IllegalStateException("Tree must be built");
    }
    return fullyDefined;
}

@Override
public boolean isComplete() {
    if (!built) return false;
    
    // Check all nodes up to actual depth are non-EMPTY
    int maxIndex = (1 << (actualDepth + 1)) - 1;
    for (int i = 0; i < maxIndex; i++) {
        if (nodeTypes[i] == NodeType.EMPTY) {
            return false;
        }
    }
    return true;
}

@Override
public List<DecisionBranch<I, O>> getAllBranches() {
    List<DecisionBranch<I, O>> branches = new ArrayList<>();
    collectBranches(0, 0, branches);
    return branches;
}

private void collectBranches(int index, int depth, List<DecisionBranch<I, O>> branches) {
    if (index >= arraySize || nodeTypes[index] == NodeType.EMPTY) {
        return;
    }
    branches.add(new ArrayDecisionBranch<>(this, index));
    if (nodeTypes[index] == NodeType.CONDITION) {
        collectBranches(2 * index + 1, depth + 1, branches);
        collectBranches(2 * index + 2, depth + 1, branches);
    }
}
```

#### Build Method

```java
@Override
public BinaryDecisionTree<I, O> build() {
    if (nodeTypes[0] == NodeType.EMPTY) {
        throw new IllegalStateException("Tree is empty");
    }
    
    // Validate tree structure
    if (!isComplete()) {
        throw new IllegalStateException("Tree is incomplete");
    }
    
    // Calculate actual depth
    actualDepth = calculateDepth(0);
    
    // Check if fully defined
    fullyDefined = checkFullyDefined(0, 0, actualDepth);
    
    built = true;
    currentIndex = 0;
    return this;
}

private int calculateDepth(int index) {
    if (index >= arraySize || nodeTypes[index] == NodeType.EMPTY) {
        return -1;
    }
    if (nodeTypes[index] == NodeType.OUTCOME) {
        return 0;
    }
    int leftDepth = calculateDepth(2 * index + 1);
    int rightDepth = calculateDepth(2 * index + 2);
    return 1 + Math.max(leftDepth, rightDepth);
}

private boolean checkFullyDefined(int index, int currentDepth, int targetDepth) {
    if (index >= arraySize || nodeTypes[index] == NodeType.EMPTY) {
        return currentDepth == targetDepth + 1;
    }
    if (nodeTypes[index] == NodeType.OUTCOME) {
        return currentDepth == targetDepth;
    }
    return checkFullyDefined(2 * index + 1, currentDepth + 1, targetDepth)
        && checkFullyDefined(2 * index + 2, currentDepth + 1, targetDepth);
}
```

### Copy Method

```java
@Override
public BinaryDecisionTree<I, O> copy() {
    ArrayDecisionTree<I, O> copy = new ArrayDecisionTree<>(maxDepth);
    copy.actualDepth = this.actualDepth;
    copy.fullyDefined = this.fullyDefined;
    copy.built = this.built;
    
    // Fast array copy
    System.arraycopy(this.nodeTypes, 0, copy.nodeTypes, 0, arraySize);
    
    // Deep copy conditions and outcomes
    for (int i = 0; i < arraySize; i++) {
        if (this.conditions[i] != null) {
            copy.conditions[i] = this.conditions[i];  // Predicates are typically stateless
        }
        if (this.outcomes[i] != null) {
            copy.outcomes[i] = this.outcomes[i];  // Functions are typically stateless
        }
    }
    
    return copy;
}
```

## ArrayDecisionBranch

```java
class ArrayDecisionBranch<I, O> implements DecisionBranch<I, O> {
    private final ArrayDecisionTree<I, O> tree;
    private final int rootIndex;
    
    ArrayDecisionBranch(ArrayDecisionTree<I, O> tree, int rootIndex) {
        this.tree = tree;
        this.rootIndex = rootIndex;
    }
    
    @Override
    public DecisionBranch<I, O> getTrueBranch() {
        checkConditionNode();
        int childIndex = 2 * rootIndex + 1;
        checkChildIndex(childIndex);
        return new ArrayDecisionBranch<>(tree, childIndex);
    }
    
    @Override
    public DecisionBranch<I, O> getFalseBranch() {
        checkConditionNode();
        int childIndex = 2 * rootIndex + 2;
        checkChildIndex(childIndex);
        return new ArrayDecisionBranch<>(tree, childIndex);
    }
    
    @Override
    public int getDepth() {
        return tree.calculateDepth(rootIndex);
    }
    
    private void checkConditionNode() {
        if (tree.nodeTypes[rootIndex] != NodeType.CONDITION) {
            throw new IllegalStateException("Not a condition node");
        }
    }
    
    private void checkChildIndex(int index) {
        if (index >= tree.arraySize || tree.nodeTypes[index] == NodeType.EMPTY) {
            throw new IllegalStateException("Child does not exist");
        }
    }
}
```

## Complexity Analysis

| Operation | Time | Space |
|-----------|------|-------|
| `decide()` | O(depth) | O(1) |
| `goToParent()` | O(1) | O(1) |
| `goToSibling()` | O(1) | O(1) |
| `copy()` | O(2^maxDepth) | O(2^maxDepth) |
| `build()` | O(2^maxDepth) | O(depth) stack |

## Usage Example

```java
// Create tree with max depth 3
ArrayDecisionTree<Integer, String> tree = new ArrayDecisionTree<>(3);

// Build decision tree
tree.builder()
    .insertCondition(x -> x > 0)      // Root: x > 0?
    .goToTrueChild()                   // Left: positive
        .insertOutcome("positive")
    .goToSibling()                     // Right: non-positive
        .insertOutcome("non-positive")
    .goToParent()
    .build();

// Use tree
String result = tree.decide(5);  // "positive"
```

## See Also

- [Performance](../architecture/performance.md) - Complexity comparison
- [Enhancements](enhancements.md) - Future improvements
- [Builder API](../api/builder-pattern.md) - Builder usage guide

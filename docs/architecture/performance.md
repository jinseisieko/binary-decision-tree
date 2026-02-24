# Performance Characteristics

This document analyzes time and space complexity of tree operations.

## Complexity Summary

| Operation | DynamicDecisionTree | ArrayDecisionTree (planned) |
|-----------|---------------------|----------------------------|
| `decide()` | O(depth) | O(depth) |
| `copy()` | O(n) | O(2^maxDepth) |
| `getDepth()` | O(1)* | O(1) |
| `isFullyDefined()` | O(n) | O(n) |
| `goToParent()` | O(n)** | O(1) |
| `goToSibling()` | O(n)** | O(1) |
| `insertCondition()` | O(n)** | O(1) |
| `build()` | O(n) | O(1) |

\* After build, cached value
\** Due to parent traversal

---

## DynamicDecisionTree Analysis

### Space Complexity

```
┌─────────────────────────────────────────────────────┐
│ Memory Layout (Pointer-Based)                       │
├─────────────────────────────────────────────────────┤
│ Each ConditionNode:                                 │
│   - Object header:     12 bytes                    │
│   - condition ref:      4 bytes                    │
│   - trueChild ref:      4 bytes                    │
│   - falseChild ref:     4 bytes                    │
│   - Alignment padding:   8 bytes                    │
│   ────────────────────────────────                  │
│   Total per node:       32 bytes                    │
│                                                     │
│ Each OutcomeNode:                                   │
│   - Object header:     12 bytes                    │
│   - handler ref:        4 bytes                    │
│   - trueChild ref:      4 bytes (inherited)        │
│   - falseChild ref:     4 bytes (inherited)        │
│   - Alignment padding:   8 bytes                    │
│   ────────────────────────────────                  │
│   Total per node:       32 bytes                    │
└─────────────────────────────────────────────────────┘
```

**Total Memory:** O(n) where n = number of nodes

```
For a perfect binary tree of depth d:
- Total nodes: n = 2^(d+1) - 1
- Memory: ~32n bytes

Example:
├── depth=5:  63 nodes  → ~2 KB
├── depth=10: 2047 nodes → ~65 KB
└── depth=15: 65535 nodes → ~2 MB
```

### Time Complexity Details

#### `decide(I input)` - O(depth)

```java
// Traverses from root to leaf
public O decide(I input) {
    return root.execute(input);  // Recursive descent
}

// ConditionNode.execute()
if (condition.test(input)) {
    return getTrueChild().execute(input);  // One recursive call
} else {
    return getFalseChild().execute(input);
}

// Depth of recursion = tree depth
// Time: O(depth)
// Stack: O(depth)
```

#### `copy()` - O(n)

```java
// Deep copy visits every node
public BinaryDecisionTree<I, O> copy() {
    if (this.root != null) {
        copy.root = this.root.copy();  // Recursive copy
    }
}

// Visits each node exactly once
// Time: O(n)
// Space: O(n) for new tree + O(depth) stack
```

#### `goToParent()` - O(n)

```java
// Must search tree to find parent
private AbstractDecisionNode<I, O> findParent(root, child) {
    if (root.getTrueChild() == child || root.getFalseChild() == child) {
        return root;
    }
    // Recursive search of entire subtree
    found = findParent(root.getTrueChild(), child);
    if (found != null) return found;
    return findParent(root.getFalseChild(), child);
}

// Worst case: visits all nodes
// Time: O(n)
```

**Improvement:** Add parent reference → O(1)

#### `isFullyDefined()` - O(n)

```java
// Checks all paths have equal depth
public boolean isFullyDefined() {
    int depth = calculateDepth(tree.root);  // O(n)
    return checkAllPathsHaveDepth(tree.root, 0, depth);  // O(n)
}

// Visits each node twice
// Time: O(n)
```

---

## ArrayDecisionTree Analysis (Planned)

### Space Complexity

```
┌─────────────────────────────────────────────────────┐
│ Memory Layout (Array-Based)                         │
├─────────────────────────────────────────────────────┤
│ Fixed array size: 2^(maxDepth+1) - 1                │
│                                                     │
│ Per index:                                          │
│   - NodeType enum:      4 bytes                    │
│   - condition ref:      4 bytes (or null)          │
│   - outcome ref:        4 bytes (or null)          │
│   ────────────────────────────────                  │
│   Total per slot:       12 bytes                    │
│                                                     │
│ No object overhead per node!                        │
└─────────────────────────────────────────────────────┘
```

**Total Memory:** O(2^maxDepth) fixed

```
Comparison at maxDepth=10:
├── Dynamic: 2047 nodes × 32 bytes = 65,504 bytes
└── Array:   2047 slots × 12 bytes = 24,564 bytes

Array uses ~37% memory for dense trees!
```

### Time Complexity Details

#### Index Calculation

```
Array indexing (heap-style):
├── Root: index 0
├── Left child (true):  2*i + 1
└── Right child (false): 2*i + 2

Parent: (i - 1) / 2

Example for depth=3 tree:
                    [0]
                  /     \
               [1]       [2]
              /   \      /  \
           [3]    [4]  [5]  [6]
```

#### `decide(I input)` - O(depth)

```java
public O decide(I input) {
    int index = 0;  // Start at root
    while (true) {
        if (nodeTypes[index] == OUTCOME) {
            return outcomes[index].apply(input);
        }
        // CONDITION node
        if (conditions[index].test(input)) {
            index = 2 * index + 1;  // Go left
        } else {
            index = 2 * index + 2;  // Go right
        }
    }
}

// Iterative (no recursion)
// Time: O(depth)
// Space: O(1) - no stack!
```

#### `goToParent()` - O(1)

```java
public DecisionTreeBuilder<I, O> goToParent() {
    if (currentIndex == 0) {
        throw new IllegalStateException("At root");
    }
    currentIndex = (currentIndex - 1) / 2;  // O(1)!
    return this;
}
```

#### `copy()` - O(2^maxDepth)

```java
public BinaryDecisionTree<I, O> copy() {
    ArrayDecisionTree<I, O> copy = new ArrayDecisionTree<>(maxDepth);
    System.arraycopy(this.nodeTypes, 0, copy.nodeTypes, 0, size);
    // ... copy arrays
    return copy;
}

// Array copy is fast but copies entire allocated space
// Time: O(2^maxDepth)
```

---

## Comparative Analysis

### When to Use DynamicDecisionTree

✅ **Use when:**
- Tree is sparse (many missing nodes)
- Tree structure changes frequently
- Memory is not critical
- Need flexible depth

❌ **Avoid when:**
- Tree is dense and fixed
- Performance-critical navigation
- Memory-constrained environment

### When to Use ArrayDecisionTree

✅ **Use when:**
- Tree is dense (complete or near-complete)
- Fixed maximum depth known
- Performance-critical (`goToParent`, `goToSibling`)
- Memory efficiency important

❌ **Avoid when:**
- Tree is very sparse
- Depth varies significantly
- Need dynamic growth

---

## Optimization Recommendations

### 1. Add Parent Reference (High Priority)

```java
abstract class AbstractDecisionNode<I, O> {
    private AbstractDecisionNode<I, O> parent;  // ADD
    
    // Now O(1) instead of O(n)
    public DecisionTreeBuilder<I, O> goToParent() {
        currentNode = currentNode.parent;
        return this;
    }
}
```

**Impact:**
- `goToParent()`: O(n) → O(1)
- `goToSibling()`: O(n) → O(1)
- `insertCondition()`: O(n) → O(1)
- Memory: +4 bytes per node

### 2. Cache Depth Calculation (Medium Priority)

```java
class DynamicDecisionTree<I, O> {
    private int depth = -1;
    private boolean depthCalculated = false;
    
    public int getDepth() {
        if (!depthCalculated) {
            depth = calculateDepth(root);
            depthCalculated = true;
        }
        return depth;
    }
    
    // Invalidate cache on modification
    private void invalidateCache() {
        depthCalculated = false;
    }
}
```

### 3. Iterative Traversal (Low Priority)

```java
// Replace recursive with iterative
public O decide(I input) {
    AbstractDecisionNode<I, O> current = root;
    while (current instanceof ConditionNode) {
        ConditionNode<I, O> cond = (ConditionNode<I, O>) current;
        current = cond.condition.test(input) 
            ? cond.getTrueChild() 
            : cond.getFalseChild();
    }
    return ((OutcomeNode<I, O>) current).execute(input);
}
```

**Benefit:** Eliminates stack overflow risk for deep trees

---

## Benchmark Guidelines

### Measuring `decide()` Performance

```java
@Benchmark
public O benchmarkDecide() {
    return tree.decide(testInput);
}

// Expected: O(depth) = O(log n) for balanced tree
```

### Measuring Memory Usage

```java
// Create trees of varying depths
for (int depth = 5; depth <= 20; depth++) {
    BinaryDecisionTree<Integer, Integer> tree = 
        buildCompleteTree(depth);
    
    long memory = getMemoryUsage(tree);
    System.out.printf("depth=%d, memory=%d bytes%n", depth, memory);
}
```

---

## See Also

- [Architecture Overview](overview.md) - System structure
- [ArrayDecisionTree Spec](../design/array-decision-tree.md) - Implementation details
- [Enhancements](../design/enhancements.md) - Optimization proposals

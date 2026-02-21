# Tree Operations Guide

This document covers operations available on `BinaryDecisionTree` instances.

## Core Operations

### decide(I input)

Executes the tree with the given input and returns the result.

```java
BinaryDecisionTree<Integer, String> tree = buildTree();

String result = tree.decide(42);
System.out.println("Result: " + result);
```

**Parameters:**
- `input` - The input value to evaluate

**Returns:**
- The output value determined by the tree

**Throws:**
- `IllegalStateException` - if tree is not built
- `NullPointerException` - if input is null

**Complexity:** O(depth)

---

### copy()

Creates a deep copy of the tree.

```java
BinaryDecisionTree<Integer, String> original = buildTree();
BinaryDecisionTree<Integer, String> copy = original.copy();

// Modifications to copy don't affect original
copy.builder()
    .goToRoot()
    .insertCondition(x -> x < 0)
    .build();
```

**Returns:**
- A new `BinaryDecisionTree` with identical structure

**Complexity:** O(n) where n = number of nodes

---

### getDepth()

Returns the depth of the tree (edges in longest path from root to leaf).

```java
BinaryDecisionTree<Integer, String> tree = buildTree();
int depth = tree.getDepth();
System.out.println("Tree depth: " + depth);
```

**Returns:**
- The depth of the tree

**Throws:**
- `IllegalStateException` - if tree is empty

**Complexity:** O(1) (cached after build)

---

### isFullyDefined()

Checks if all paths from root to leaf have the same length (perfect binary tree).

```java
BinaryDecisionTree<Integer, String> tree = buildTree();

if (tree.isFullyDefined()) {
    System.out.println("All paths have equal length");
} else {
    System.out.println("Tree has unbalanced paths");
}
```

**Returns:**
- `true` if all paths have equal length, `false` otherwise

**Throws:**
- `IllegalStateException` - if tree is empty

**Complexity:** O(1) (cached after build)

---

### builder()

Returns a builder for modifying the tree.

```java
BinaryDecisionTree<Integer, String> tree = buildTree();

// Create modified version
BinaryDecisionTree<Integer, String> modified = tree.builder()
    .goToRoot()
    .goToTrueChild()
    .insertOutcome("new outcome")
    .build();
```

**Returns:**
- A `DecisionTreeBuilder` operating on a copy of the tree

**Note:** The original tree is not modified

---

## Proposed Operations (Future)

### toJson() / fromJson()

Serialize/deserialize tree to/from JSON.

```java
// Future API
String json = tree.toJson();

// Save to file
Files.writeString(Paths.get("tree.json"), json);

// Load from file
String loadedJson = Files.readString(Paths.get("tree.json"));
BinaryDecisionTree<Integer, String> loaded = 
    BinaryDecisionTree.fromJson(loadedJson);
```

---

### toDot()

Export tree to GraphViz DOT format.

```java
// Future API
String dot = tree.toDot();
System.out.println(dot);

// Output:
// digraph DecisionTree {
//     node0 [label="x > 0"];
//     node1 [label="positive" shape=box];
//     node2 [label="non-positive" shape=box];
//     node0 -> node1 [label="true"];
//     node0 -> node2 [label="false"];
// }
```

---

### tracePath(I input)

Get the decision path for a given input.

```java
// Future API
DecisionPath<Integer, String> path = tree.tracePath(42);

for (PathStep<Integer, String> step : path.getSteps()) {
    System.out.printf("Depth %d: %s → %s%n", 
        step.depth(),
        step.condition(),
        step.result() ? "true" : "false");
}
```

---

### getStatistics()

Get tree statistics.

```java
// Future API
TreeStatistics stats = tree.getStatistics();

System.out.println("Node count: " + stats.getNodeCount());
System.out.println("Condition count: " + stats.getConditionCount());
System.out.println("Outcome count: " + stats.getOutcomeCount());
System.out.println("Depth: " + stats.getDepth());
System.out.println("Balance factor: " + stats.getBalanceFactor());
```

---

## Iteration (Future)

### breadthFirst()

Iterate over nodes in breadth-first order.

```java
// Future API
for (DecisionBranch<Integer, String> branch : tree.breadthFirst()) {
    System.out.println("Node at depth: " + branch.getDepth());
}
```

---

### depthFirst()

Iterate over nodes in depth-first order.

```java
// Future API
for (DecisionBranch<Integer, String> branch : tree.depthFirst()) {
    System.out.println("Node at depth: " + branch.getDepth());
}
```

---

### atDepth(int)

Iterate over nodes at a specific depth.

```java
// Future API
for (DecisionBranch<Integer, String> branch : tree.atDepth(2)) {
    System.out.println("Node at depth 2");
}
```

---

## Visitor Pattern (Future)

### accept(Visitor)

Apply a visitor to the tree.

```java
// Future API
tree.accept(new DecisionTreeVisitor<Integer, String>() {
    @Override
    public void visit(ConditionNode<Integer, String> node, int depth) {
        System.out.println("Condition at depth " + depth);
    }
    
    @Override
    public void visit(OutcomeNode<Integer, String> node, int depth) {
        System.out.println("Outcome at depth " + depth);
    }
});
```

---

## Example: Complete Workflow

```java
// 1. Create tree
BinaryDecisionTree<Integer, String> tree = new DynamicDecisionTree<>()
    .builder()
    .insertCondition(x -> x > 0)
    .goToTrueChild()
    .insertOutcome("positive")
    .goToSibling()
    .insertOutcome("non-positive")
    .goToParent()
    .build();

// 2. Execute
String result = tree.decide(5);  // "positive"

// 3. Query metadata
int depth = tree.getDepth();           // 1
boolean defined = tree.isFullyDefined(); // true

// 4. Copy and modify
BinaryDecisionTree<Integer, String> modified = tree.builder()
    .goToRoot()
    .goToTrueChild()
    .insertOutcome("very positive")
    .goToParent()
    .build();

// 5. Verify copy didn't affect original
String originalResult = tree.decide(5);     // "positive"
String modifiedResult = modified.decide(5); // "very positive"
```

---

## Error Handling

### Unbuilt Tree

```java
BinaryDecisionTree<Integer, String> tree = new DynamicDecisionTree<>();

// ❌ Throws IllegalStateException
tree.decide(5);

// ✅ Build first
tree = tree.builder()
    .insertOutcome(0)
    .build();
tree.decide(5);  // Works
```

### Null Input

```java
BinaryDecisionTree<Integer, String> tree = buildTree();

// ❌ Throws NullPointerException
tree.decide(null);

// ✅ Provide valid input
tree.decide(5);
```

### Empty Tree

```java
BinaryDecisionTree<Integer, String> tree = new DynamicDecisionTree<>();

// ❌ Throws IllegalStateException
tree.getDepth();
tree.isFullyDefined();

// ✅ Check if empty first
if (tree.builder().getDepth() > 0) {
    // Safe to use
}
```

---

## Performance Considerations

### decide() Performance

```java
// O(depth) - proportional to tree depth
// For balanced tree: O(log n)
// For skewed tree: O(n)

// Optimize by keeping trees balanced
// Use isFullyDefined() to verify balance
```

### copy() Performance

```java
// O(n) - visits every node
// Consider builder modification instead of copy for large trees

// ✅ Efficient for small changes
tree.builder()
    .goToRoot()
    .goToTrueChild()
    .insertOutcome("new")
    .build();
```

---

## See Also

- [Builder API](builder-pattern.md) - Builder pattern guide
- [Usage Examples](../examples/usage-examples.md) - Code examples
- [Performance](../architecture/performance.md) - Complexity analysis

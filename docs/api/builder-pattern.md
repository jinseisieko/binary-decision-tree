# Builder Pattern API Guide

This document provides a comprehensive guide to using the `DecisionTreeBuilder` API.

## Overview

The Builder pattern enables fluent construction and modification of decision trees through a navigation-based API.

```java
// Basic usage pattern
BinaryDecisionTree<Input, Output> tree = new DynamicDecisionTree<>()
    .builder()
    // Navigate and build structure
    .insertCondition(input -> /* condition */)
    .goToTrueChild()
    .insertOutcome(/* value */)
    .goToParent()
    // Finalize
    .build();
```

---

## Navigation Methods

### goToTrueChild()

Moves the builder's position to the true (left) child of the current node.

```java
builder.insertCondition(x -> x > 0)
       .goToTrueChild()  // Now positioned at true child
       .insertOutcome("positive");
```

**Throws:**
- `IllegalStateException` - if tree is empty, current node is not a condition, or true child doesn't exist (auto-created as placeholder)

---

### goToFalseChild()

Moves the builder's position to the false (right) child of the current node.

```java
builder.insertCondition(x -> x > 0)
       .goToFalseChild()  // Now positioned at false child
       .insertOutcome("non-positive");
```

**Throws:**
- `IllegalStateException` - if tree is empty, current node is not a condition, or false child doesn't exist (auto-created as placeholder)

---

### goToSibling()

Moves to the sibling of the current node (true ↔ false).

```java
builder.insertCondition(x -> x > 0)
       .goToTrueChild()
       .insertOutcome("positive")
       .goToSibling()  // Now at false child
       .insertOutcome("non-positive");
```

**Throws:**
- `IllegalStateException` - if at root or sibling doesn't exist (auto-created as placeholder)

---

### goToParent()

Moves to the parent of the current node.

```java
builder.insertCondition(x -> x > 0)
       .goToTrueChild()
       .insertOutcome("positive")
       .goToParent()  // Back at condition node
       .goToFalseChild();
```

**Throws:**
- `IllegalStateException` - if at root or no parent exists

---

### goToRoot()

Moves to the root of the tree.

```java
// Navigate deep into tree
builder.insertCondition(x -> x > 0)
       .goToTrueChild()
       .insertCondition(x -> x > 10)
       .goToFalseChild()
       .insertOutcome("medium");

// Reset to root
builder.goToRoot();  // Ready to build from top
```

---

## Modification Methods

### insertCondition(Predicate<I>)

Replaces the current node with a condition node.

```java
builder.insertCondition(x -> x > 0)  // x > 0?
       .insertCondition(x -> x > 10); // Replaces previous condition
```

**Parameters:**
- `condition` - Predicate that determines branching

**Throws:**
- `NullPointerException` - if condition is null

---

### insertOutcome(Function<I, O>)

Replaces the current node with an outcome node using a handler function.

```java
builder.insertOutcome(x -> x * 2);  // Returns x * 2
```

**Parameters:**
- `handler` - Function that computes the output

**Throws:**
- `NullPointerException` - if handler is null

---

### insertOutcome(O)

Replaces the current node with an outcome node returning a constant value.

```java
builder.insertOutcome("positive");  // Always returns "positive"
```

**Parameters:**
- `value` - Constant output value

**Throws:**
- `NullPointerException` - if value is null

---

### clearTree()

Removes all nodes from the tree.

```java
builder.clearTree();  // Tree is now empty
```

**Returns:** This builder for method chaining

---

### clearSubtree()

Clears the children of the current condition node.

```java
builder.insertCondition(x -> x > 0)
       .goToTrueChild()
       .insertOutcome("positive")
       .goToParent()
       .goToFalseChild()
       .insertOutcome("negative")
       .goToParent()
       .clearSubtree();  // Both children removed
```

**Note:** Only affects `ConditionNode` instances

---

### insertBranch(DecisionBranch<I, O>)

Inserts a branch (subtree) at the current position.

```java
DecisionBranch<Integer, String> branch = getExistingBranch();

builder.insertCondition(x -> x > 0)
       .goToTrueChild()
       .insertBranch(branch);  // Attach subtree
```

**Parameters:**
- `branch` - Subtree to insert

**Throws:**
- `NullPointerException` - if branch is null
- `IllegalArgumentException` - if branch is not compatible type

---

## Query Methods

### asBranch()

Gets a reference to the current subtree.

```java
DecisionBranch<Integer, String> branch = builder
    .insertCondition(x -> x > 0)
    .goToTrueChild()
    .insertOutcome("positive")
    .goToParent()
    .asBranch();  // Reference to entire subtree
```

**Throws:**
- `IllegalStateException` - if current node is null

---

### asBranchOfSubtree()

Gets a copy of the current subtree as a branch.

```java
DecisionBranch<Integer, String> branch = builder
    .asBranchOfSubtree();  // Independent copy
```

**Use Case:** Extract subtree for reuse in another tree

---

### getAllBranches()

Returns all branches (nodes) in the tree.

```java
List<DecisionBranch<Integer, String>> allBranches = 
    builder.getAllBranches();

for (DecisionBranch<Integer, String> branch : allBranches) {
    System.out.println("Branch depth: " + branch.getDepth());
}
```

---

### getAllBranchesWithDepth(int)

Returns all branches at a specific depth.

```java
// Get all nodes at depth 2
List<DecisionBranch<Integer, String>> depth2Nodes = 
    builder.getAllBranchesWithDepth(2);
```

**Parameters:**
- `depth` - Target depth (0 = root)

**Throws:**
- `IllegalArgumentException` - if depth is negative

---

### getDepth()

Returns the current tree depth.

```java
int depth = builder.getDepth();
System.out.println("Tree depth: " + depth);
```

**Throws:**
- `IllegalStateException` - if tree is empty

---

### isComplete()

Checks if all condition nodes have both children.

```java
if (builder.isComplete()) {
    System.out.println("Tree is complete");
}
```

---

### isFullyDefined()

Checks if all paths from root to leaf have the same length.

```java
if (builder.isFullyDefined()) {
    System.out.println("Tree is a perfect binary tree");
}
```

---

### build()

Finalizes the tree and returns the built `BinaryDecisionTree`.

```java
BinaryDecisionTree<Integer, String> tree = builder
    .insertCondition(x -> x > 0)
    .goToTrueChild()
    .insertOutcome("positive")
    .goToSibling()
    .insertOutcome("non-positive")
    .goToParent()
    .build();
```

**Throws:**
- `IllegalStateException` - if tree is empty or incomplete

---

## Complete Examples

### Example 1: Simple Binary Classification

```java
BinaryDecisionTree<Integer, String> classifier = new DynamicDecisionTree<>()
    .builder()
    .insertCondition(x -> x >= 0)
    .goToTrueChild()
    .insertOutcome("non-negative")
    .goToSibling()
    .insertOutcome("negative")
    .goToParent()
    .build();

// Use the tree
String result = classifier.decide(5);   // "non-negative"
String result2 = classifier.decide(-3); // "negative"
```

---

### Example 2: Multi-Level Decision Tree

```java
BinaryDecisionTree<Integer, String> tree = new DynamicDecisionTree<>()
    .builder()
    // Level 1
    .insertCondition(x -> x > 0)
    
    // True branch: positive numbers
    .goToTrueChild()
        .insertCondition(x -> x > 10)
        .goToTrueChild()
            .insertOutcome("large positive")
        .goToSibling()
            .insertOutcome("small positive")
        .goToParent()
    
    // False branch: non-positive numbers
    .goToSibling()
        .insertCondition(x -> x < 0)
        .goToTrueChild()
            .insertOutcome("negative")
        .goToSibling()
            .insertOutcome("zero")
        .goToParent()
    
    .goToParent()
    .build();
```

---

### Example 3: Tree Modification

```java
// Build initial tree
BinaryDecisionTree<Integer, String> tree = new DynamicDecisionTree<>()
    .builder()
    .insertCondition(x -> x > 0)
    .goToTrueChild()
    .insertOutcome("positive")
    .goToSibling()
    .insertOutcome("non-positive")
    .goToParent()
    .build();

// Modify via builder
DecisionTreeBuilder<Integer, String> builder = tree.builder();
builder.goToRoot()
       .goToFalseChild()
       .clearSubtree()
       .insertCondition(x -> x == 0)
       .goToTrueChild()
       .insertOutcome("zero")
       .goToSibling()
       .insertOutcome("negative")
       .goToParent();

BinaryDecisionTree<Integer, String> modified = builder.build();
```

---

### Example 4: Reusing Subtrees

```java
// Create a common subtree
DecisionBranch<Integer, String> commonBranch = new DynamicDecisionTree<>()
    .builder()
    .insertCondition(x -> x > 100)
    .goToTrueChild()
    .insertOutcome("very large")
    .goToSibling()
    .insertOutcome("moderate")
    .goToParent()
    .asBranchOfSubtree();

// Use in multiple trees
BinaryDecisionTree<Integer, String> tree1 = new DynamicDecisionTree<>()
    .builder()
    .insertCondition(x -> x > 0)
    .goToTrueChild()
    .insertBranch(commonBranch)  // Reuse branch
    .goToSibling()
    .insertOutcome("non-positive")
    .goToParent()
    .build();
```

---

## Best Practices

### 1. Always Call build()

```java
// ❌ Wrong - tree not finalized
DecisionTreeBuilder<I, O> builder = tree.builder();
builder.insertCondition(x -> x > 0);
// Missing: builder.build();

// ✅ Correct
BinaryDecisionTree<I, O> built = builder
    .insertCondition(x -> x > 0)
    .build();
```

### 2. Return to Root After Building

```java
// After build(), builder is at last position
builder.build();

// Reset before next operation
builder.goToRoot();
```

### 3. Check isComplete() Before build()

```java
if (!builder.isComplete()) {
    throw new IllegalStateException("Tree has missing children");
}
builder.build();
```

### 4. Use asBranchOfSubtree() for Extraction

```java
// ✅ Creates independent copy
DecisionBranch<I, O> branch = builder.asBranchOfSubtree();

// ❌ Original tree may be modified
DecisionBranch<I, O> branch = builder.asBranch();
```

---

## See Also

- [Usage Examples](../examples/usage-examples.md) - More code examples
- [Design Patterns](../architecture/design-patterns.md) - Builder pattern analysis
- [API Reference](../api/tree-operations.md) - Tree operation documentation

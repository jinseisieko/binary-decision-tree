# Architecture Overview

## System Architecture

The Binary Decision Tree library consists of two main implementations sharing a common interface.

```
┌─────────────────────────────────────────────────────────────────────┐
│                         BinaryDecisionTree<I,O>                      │
│  ┌──────────────────────────────────────────────────────────────┐   │
│  │ O decide(I input)                                             │   │
│  │ DecisionTreeBuilder<I,O> builder()                            │   │
│  │ BinaryDecisionTree<I,O> copy()                                │   │
│  │ int getDepth()                                                │   │
│  │ boolean isFullyDefined()                                      │   │
│  └──────────────────────────────────────────────────────────────┘   │
└─────────────────────────────┬───────────────────────────────────────┘
                              │ implements
              ┌───────────────┴───────────────┐
              │                               │
    ┌─────────▼────────┐           ┌─────────▼────────┐
    │ DynamicDecision  │           │ ArrayDecision    │
    │     Tree         │           │     Tree         │
    │                  │           │                  │
    │ • Pointer-based  │           │ • Array-based    │
    │ • Dynamic size   │           │ • Fixed maxDepth │
    │ • O(n) parent    │           │ • O(1) access    │
    │ • Flexible       │           │ • Cache-friendly │
    └────────┬─────────┘           └────────┬─────────┘
             │                              │
             └──────────┬───────────────────┘
                        │
              ┌─────────▼──────────┐
              │  DecisionBranch    │
              │  (subtree ref)     │
              └────────────────────┘
```

## Core Components

### 1. BinaryDecisionTree (Interface)

The main interface defining tree operations:

```java
public interface BinaryDecisionTree<I, O> {
    O decide(I input);                              // Execute tree
    DecisionTreeBuilder<I, O> builder();            // Get builder
    BinaryDecisionTree<I, O> copy();                // Deep copy
    int getDepth();                                 // Tree depth
    boolean isFullyDefined();                       // Perfect tree check
}
```

### 2. DynamicDecisionTree (Implementation)

Pointer-based implementation using node references:

```
DynamicDecisionTree
├── depth: int
├── root: AbstractDecisionNode
└── fullyDefined: boolean

DynamicDecisionTree.Builder
├── tree: DynamicDecisionTree
├── currentNode: AbstractDecisionNode
└── [builder methods]
```

**Node Hierarchy:**
```
AbstractDecisionNode<I, O>
├── trueChild: AbstractDecisionNode
├── falseChild: AbstractDecisionNode
├── clearChildren(): void
├── isComplete(): boolean
├── execute(I): O
└── copy(): AbstractDecisionNode
    │
    ├── ConditionNode<I, O>
    │   ├── condition: Predicate<I>
    │   └── execute(I): O  // Routes to true/false child
    │
    └── OutcomeNode<I, O>
        ├── handler: Function<I, O>
        └── execute(I): O  // Returns computed value
```

### 3. DecisionBranch (Interface)

Reference to a subtree for manipulation:

```java
public interface DecisionBranch<I, O> {
    DecisionBranch<I, O> getTrueBranch();
    DecisionBranch<I, O> getFalseBranch();
    int getDepth();
}
```

## Data Flow

### Tree Construction Flow

```
1. Create Tree
   DynamicDecisionTree<I,O> tree = new DynamicDecisionTree<>();

2. Get Builder
   DecisionTreeBuilder<I,O> builder = tree.builder();

3. Build Structure
   builder.insertCondition(x -> x > 0)
          .goToTrueChild()
          .insertOutcome(x -> 1)
          .goToSibling()
          .insertOutcome(x -> -1)
          .goToParent();

4. Finalize
   BinaryDecisionTree<I,O> builtTree = builder.build();

5. Execute
   O result = builtTree.decide(input);
```

### Navigation Flow

```
                    root (depth 0)
                   /    \
        goToTrue /      \ goToFalse
                 /        \
        (depth 1)        (depth 1)
           │                │
           │ goToParent     │ goToSibling
           │                │
           └───────┬────────┘
                   │
                (depth 0)
                   │
              goToRoot
                   │
                (root)
```

## Key Design Decisions

### 1. Builder Pattern
- Enables fluent tree construction
- Maintains navigation state
- Validates before building

### 2. Copy-on-Write Semantics
- `builder()` works on tree copy
- Original tree unchanged until `build()`
- Ensures immutability of built trees

### 3. Lazy Child Creation
- Navigation auto-creates placeholder nodes
- Enables flexible tree modification
- Prevents null pointer exceptions

### 4. Perfect Binary Tree Definition
- `isFullyDefined()` = all paths have equal length
- Enforces balanced tree structure
- Important for predictable performance

## Type Parameters

| Parameter | Description | Example |
|-----------|-------------|---------|
| `I` | Input type | `Integer`, `String`, `Event` |
| `O` | Output type | `Integer`, `String`, `Decision` |

## Thread Safety

**Current Status:** Not thread-safe

- Trees are immutable after `build()`
- Builder operations are not synchronized
- For concurrent access:
  - Build once, share across threads (safe)
  - Use separate builders per thread
  - Consider immutable wrapper for safety

## Memory Model

### DynamicDecisionTree
```
Tree:     O(n) nodes
Builder:  O(1) state reference
Copy:     O(n) full duplication
```

### ArrayDecisionTree (Planned)
```
Tree:     O(2^maxDepth) fixed array
Builder:  O(1) index tracking
Copy:     O(2^maxDepth) array copy
```

## Extension Points

1. **New Node Types** - Extend `AbstractDecisionNode`
2. **New Tree Implementations** - Implement `BinaryDecisionTree`
3. **Custom Branches** - Implement `DecisionBranch`
4. **Visitors** - Add visitor pattern support
5. **Serializers** - Add JSON/XML export

## See Also

- [Design Patterns](design-patterns.md) - Pattern analysis
- [Performance](performance.md) - Complexity analysis
- [ArrayDecisionTree Spec](../design/array-decision-tree.md) - Implementation guide

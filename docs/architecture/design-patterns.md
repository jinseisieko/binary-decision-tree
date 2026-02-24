# Design Patterns

This document analyzes the design patterns used in the Binary Decision Tree library.

## Implemented Patterns

### 1. Builder Pattern ✅

**Location:** `DynamicDecisionTree.Builder`, `ArrayDecisionTree.Builder`

**Purpose:** Construct complex tree structures step-by-step

**Structure:**
```
┌─────────────────────────────────────┐
│   DecisionTreeBuilder<I, O>         │  (Interface)
│  ─────────────────────────────────  │
│  + insertCondition(Predicate)       │
│  + insertOutcome(Function/O)        │
│  + goToTrueChild()                  │
│  + goToFalseChild()                 │
│  + goToSibling()                    │
│  + goToParent()                     │
│  + goToRoot()                       │
│  + build()                          │
└─────────────────────────────────────┘
                ▲
                │ implements
    ┌───────────┴────────────┐
    │                        │
    ▼                        ▼
┌──────────────────┐  ┌──────────────────┐
│ DynamicDecision  │  │ ArrayDecision    │
│ Tree.Builder     │  │ Tree.Builder     │
│                  │  │                  │
│ - tree           │  │ - tree           │
│ - currentNode    │  │ - currentIndex   │
└──────────────────┘  └──────────────────┘
```

**Usage Example:**
```java
BinaryDecisionTree<Integer, String> tree = new DynamicDecisionTree<>()
    .builder()
    .insertCondition(x -> x > 0)
    .goToTrueChild()
    .insertOutcome("positive")
    .goToSibling()
    .insertOutcome("non-positive")
    .goToParent()
    .build();
```

**Benefits:**
- Fluent, readable API
- Encapsulates construction logic
- Validates before finalizing
- Maintains navigation state

---

### 2. Composite Pattern ✅

**Location:** `AbstractDecisionNode`, `ConditionNode`, `OutcomeNode`

**Purpose:** Treat individual nodes uniformly as part of a tree structure

**Structure:**
```
              ┌─────────────────────────┐
              │  AbstractDecisionNode   │  (Component)
              │  ─────────────────────  │
              │  + execute(I): O        │
              │  + isComplete(): bool   │
              │  + copy(): Node         │
              │  + getTrueChild()       │
              │  + getFalseChild()      │
              └───────────┬─────────────┘
                          │
         ┌────────────────┼────────────────┐
         │                │                │
         ▼                ▼                ▼
┌─────────────────┐  ┌─────────────────┐
│  ConditionNode  │  │  OutcomeNode    │
│  (Composite)    │  │  (Leaf)         │
│  ─────────────  │  │  ─────────────  │
│  - condition    │  │  - handler      │
│  - trueChild    │  │                 │
│  - falseChild   │  │                 │
└─────────────────┘  └─────────────────┘
```

**Benefits:**
- Uniform node handling
- Recursive tree operations
- Clear leaf vs internal distinction

---

### 3. Prototype Pattern ✅

**Location:** `AbstractDecisionNode.copy()`, `BinaryDecisionTree.copy()`

**Purpose:** Create deep copies of tree structures

**Implementation:**
```java
// Node-level prototype
@Override
public AbstractDecisionNode<I, O> copy() {
    return new ConditionNode<>(this);  // Copy constructor
}

// Tree-level prototype
@Override
public BinaryDecisionTree<I, O> copy() {
    DynamicDecisionTree<I, O> copy = new DynamicDecisionTree<>();
    copy.depth = this.depth;
    copy.fullyDefined = this.fullyDefined;
    if (this.root != null) {
        copy.root = this.root.copy();  // Recursive copy
    }
    return copy;
}
```

**Benefits:**
- Deep copy without exposing internals
- Preserves tree structure
- Enables safe modification via builder

---

### 4. Strategy Pattern ✅

**Location:** `Predicate<I>` conditions, `Function<I, O>` handlers

**Purpose:** Encapsulate variable decision logic and outcome computation

**Usage:**
```java
// Condition strategy
Predicate<Integer> condition = x -> x > threshold;

// Outcome strategy
Function<Integer, String> handler = x -> x * 2 > limit ? "high" : "low";

// Tree uses strategies
builder.insertCondition(condition)
       .insertOutcome(handler);
```

**Benefits:**
- Flexible decision logic
- Runtime behavior changes
- Easy to test strategies independently

---

## Proposed Patterns

### 5. Visitor Pattern 🔶

**Status:** Recommended for implementation

**Purpose:** Separate algorithms from tree structure

**Proposed Structure:**
```java
public interface DecisionTreeVisitor<I, O> {
    void visit(ConditionNode<I, O> node, int depth);
    void visit(OutcomeNode<I, O> node, int depth);
}

// Usage in nodes
abstract class AbstractDecisionNode<I, O> {
    public abstract void accept(DecisionTreeVisitor<I, O> visitor, int depth);
}

// Concrete implementation
class ConditionNode<I, O> extends AbstractDecisionNode<I, O> {
    @Override
    public void accept(DecisionTreeVisitor<I, O> visitor, int depth) {
        visitor.visit(this, depth);
        if (getTrueChild() != null) getTrueChild().accept(visitor, depth + 1);
        if (getFalseChild() != null) getFalseChild().accept(visitor, depth + 1);
    }
}
```

**Use Cases:**
- Tree visualization
- Serialization
- Validation
- Statistics collection

**Example Visitors:**
```java
// DOT export visitor
class DotVisitor implements DecisionTreeVisitor<I, O> {
    StringBuilder dot = new StringBuilder("digraph Tree {\n");
    
    public void visit(ConditionNode node, int depth) {
        dot.append("  node").append(System.identityHashCode(node))
           .append(" [label=\"").append(node.condition).append("\"]\n");
    }
    
    public void visit(OutcomeNode node, int depth) {
        dot.append("  node").append(System.identityHashCode(node))
           .append(" [label=\"").append(node.handler).append("\" shape=box]\n");
    }
}

// Validation visitor
class ValidationVisitor implements DecisionTreeVisitor<I, O> {
    List<String> errors = new ArrayList<>();
    
    public void visit(ConditionNode node, int depth) {
        if (node.getTrueChild() == null) {
            errors.add("Condition node missing true child at depth " + depth);
        }
        if (node.getFalseChild() == null) {
            errors.add("Condition node missing false child at depth " + depth);
        }
    }
}
```

---

### 6. Iterator Pattern 🔶

**Status:** Recommended for implementation

**Purpose:** Traverse tree without exposing internal structure

**Proposed Structure:**
```java
public class TreeIterator<I, O> implements Iterator<AbstractDecisionNode<I, O>> {
    private Queue<AbstractDecisionNode<I, O>> queue = new ArrayDeque<>();
    
    public TreeIterator(AbstractDecisionNode<I, O> root) {
        if (root != null) queue.offer(root);
    }
    
    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }
    
    @Override
    public AbstractDecisionNode<I, O> next() {
        AbstractDecisionNode<I, O> node = queue.poll();
        if (node instanceof ConditionNode) {
            ConditionNode<I, O> cond = (ConditionNode<I, O>) node;
            if (cond.getTrueChild() != null) queue.offer(cond.getTrueChild());
            if (cond.getFalseChild() != null) queue.offer(cond.getFalseChild());
        }
        return node;
    }
}
```

---

### 7. Null Object Pattern ✅ (Partial)

**Location:** Lazy child creation in builder

**Purpose:** Avoid null checks with placeholder objects

**Current Implementation:**
```java
@Override
public DecisionTreeBuilder<I, O> goToTrueChild() {
    AbstractDecisionNode<I, O> trueChild = currentNode.getTrueChild();
    if (trueChild == null) {
        trueChild = new OutcomeNode<>(input -> {
            throw new IllegalStateException("Reached unbuilt outcome node");
        });
        currentNode.setTrueChild(trueChild);
    }
    currentNode = trueChild;
    return this;
}
```

**Improvement Opportunity:**
```java
// Dedicated null object
class UnbuiltNode<I, O> extends OutcomeNode<I, O> {
    public UnbuiltNode() {
        super(input -> {
            throw new UnbuiltTreeException("Node not yet built");
        });
    }
    
    @Override
    public boolean isComplete() {
        return false;
    }
}
```

---

### 8. Factory Method Pattern 🔶

**Status:** Partially implemented

**Purpose:** Centralize node creation

**Current:** Direct instantiation in builder
```java
ConditionNode<I, O> newNode = new ConditionNode<>(condition);
```

**Proposed:**
```java
interface NodeFactory<I, O> {
    ConditionNode<I, O> createCondition(Predicate<I> condition);
    OutcomeNode<I, O> createOutcome(Function<I, O> handler);
    OutcomeNode<I, O> createOutcome(O value);
}

// Usage
class DefaultNodeFactory implements NodeFactory<I, O> {
    public ConditionNode<I, O> createCondition(Predicate<I> condition) {
        return new ConditionNode<>(condition);
    }
    // ...
}
```

---

## Pattern Relationships

```
┌─────────────────────────────────────────────────────────────┐
│                    Client Code                               │
└─────────────────────┬───────────────────────────────────────┘
                      │ uses
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                  Builder Pattern                             │
│  (DecisionTreeBuilder constructs the tree)                   │
└─────────────────────┬───────────────────────────────────────┘
                      │ creates
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                 Composite Pattern                            │
│  (AbstractDecisionNode tree structure)                       │
│  ┌─────────────┐    ┌─────────────┐                         │
│  │ Condition   │────│  Outcome    │                         │
│  │   (node)    │    │   (leaf)    │                         │
│  └─────────────┘    └─────────────┘                         │
└─────────────────────┬───────────────────────────────────────┘
                      │ supports
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                 Prototype Pattern                            │
│  (copy() method for deep cloning)                            │
└─────────────────────────────────────────────────────────────┘
                      │ uses
                      ▼
┌─────────────────────────────────────────────────────────────┐
│                 Strategy Pattern                             │
│  (Predicate conditions, Function handlers)                   │
└─────────────────────────────────────────────────────────────┘
```

---

## Anti-Patterns to Avoid

### 1. God Object ❌
**Risk:** Putting all logic in `DynamicDecisionTree`
**Solution:** Keep node logic in node classes

### 2. Circular Dependency ❌
**Risk:** Builder ↔ Tree circular references
**Solution:** Builder holds reference, Tree doesn't know about Builder

### 3. Exposed Internals ❌
**Risk:** Returning internal node references
**Solution:** Return copies or use `DecisionBranch` wrapper

### 4. Premature Optimization ❌
**Risk:** Array implementation before validating use cases
**Solution:** Profile first, optimize based on data

---

## See Also

- [Architecture Overview](overview.md) - System structure
- [Enhancements](../design/enhancements.md) - Future improvements
- [Builder API](../api/builder-pattern.md) - Detailed builder guide

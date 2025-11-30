# Classes
## CBDTreeNode
The base class for nodes (vertices) of binary tree. It has the level (integer) (e.g. level = 0 means that it is root of tree). 

## UML diagram
![UML diagram](assets/uml_diagram.png)
# Prototype of usage 
```java
BDTree<int, int> machine = DynamicBDTree<>();
BDTreeBuilder<int, int> builder = machine.builder();
builder.setCondition(a -> a > 10);
builder.goToTrueBranch();
builder.setOutcome(1);
builder.goBack();
builder.goToFalseBranch();
builder.setCondition(a -> a < 5);
builder.goToTrueBranch();
builder.setOutcome(2);
builder.goBack();
builder.goToFalseBranch();
builder.setOutcome(3);
int depth = machine.getDepth();
int result = machine.decide(9);

// ===================================== //

BDTree<int, int> machine = ArrayBDTree<>(2);
BDTreeBuilder<int, int> builder = machine.builder();
builder.setCondition(a -> a > 10);
builder.goToTrueBranch();
builder.setOutcome(1);
builder.goBack();
builder.goToFalseBranch();
builder.setCondition(a -> a < 5);
builder.goToTrueBranch();
builder.setOutcome(2);
builder.goBack();
builder.goToFalseBranch();
builder.setOutcome(3);
int depth = machine.getDepth();
int result = machine.decide(9);

```

# Notes
- rename library to BinDecisTree (bindecistree)

Prototype of nodes 
```java
abstract class Node<D, V> {
    public abstract V execute(D data);
}

class CNode<D, V> extends Node<D, V> {
    private Predicate<D> condition;
    private Node<D, V> trueBranch;
    private Node<D, V> falseBranch;

    public CNode(Predicate<D> condition, Node<D, V> trueBranch, Node<D, V> falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    public V execute(D data) {
        // check for nulls and other errors
        if (condition.test(data)) {
            return trueBranch.execute(data);
        } else {
            return falseBranch.execute(data);
        }
    }
}

class ONode<D, V> extends Node<D, V> {
    private V value;

    public ONode(V value) {
        this.value = value;
    }

    @Override
    public V execute(D data) {
        return value;
    }
}
```

## Description
This library works with binary decision trees. Specifically, You can create, modify, and use them for various tasks. The core interfaces are `BinDecisTree`, `BinDecisTreeBranch`, and `BinDecisTreeBuilder`. Each handles a specific role: using trees, passing tree parts, and changing structure.

### `BinDecisTree<D,V>`
It defines the main tree behavior.  
- `D` is input data type.  
- `V` is output value type.  

Use this interface to declare your trees. It gives you:

- `decide(D): V` – runs the tree and returns a result.  
- `isComplete(): boolean` – checks if every node has a condition or outcome.  
- `getDepth(): int` – returns tree depth. A single node has depth 0.  
- `toBranch(): BinDecisTreeBranch<D,V>` – turns the whole tree into a branch.  
- `getAllBranches(): List<BinDecisTreeBranch<D,V>>` – returns all root-to-leaf paths.  
- `getAllBranchesWithDepth(depth: int): List<BinDecisTreeBranch<D,V>>` – returns branches at a specific depth.

Two classes implement `BinDecisTree`:  
- `DynamicBinDecisTree` – uses linked nodes.  
- `ArrayBinDecisTree` – stores nodes in an array with index-based child access.  

Both offer the same interface but differ internally. Choose based on your performance and memory needs.

### `BinDecisTreeBranch<D,V>`
It lets you move parts of a tree between structures. Branches are read-only. They hold references to the original tree for efficiency. Each tree type has its own branch implementation. You can insert a branch into another tree using a builder.

### `BinDecisTreeBuilder<D,V>`
It handles all modifications. It starts at the root and tracks your current position. Builders never change the original tree. They return a new instance with your changes applied. This keeps your state safe and supports easy rollbacks.

Builder operations:

- `insertCondition(condition: Predicate<D>)` – adds a condition at your current position.  
- `insertOutcome(handler: Function<D,V>)` or `insertOutcome(value: V)` – adds a result value or function that returns value.  
- `goToTrueNode()` – moves to the true child.  
- `goToFalseNode()` – moves to the false child.  
- `goToSiblingNode()` – moves to the sibling node.  
- `goBack()` – moves back to the parent.  
- `clear()` – removes the node at your current position.  
- `clearSubtree()` – removes the whole subtree below your position.  
- `insertBranch(branch: BinDecisTreeBranch<D,V>)` – inserts a branch at your position.  
- `toBranch()` – converts your current subtree into a branch.  
- `getLevel()` – returns your current level in the tree.  
- `build()` – finishes and returns your new tree.

## UML diagram
![UML diagram](assets/uml_diagram.png)
## Prototype of usage 
```java
BDTree<int, int> machine = DynamicBDTree<>();
BDTreeBuilder<int, int> builder = machine.builder();
builder.insertCondition(a -> a > 10);
builder.goToTrueBranch();
builder.insertOutcome(1);
builder.goBack();
builder.goToFalseBranch();
builder.insertCondition(a -> a < 5);
builder.goToTrueBranch();
builder.insertOutcome(2);
builder.goBack();
builder.goToFalseBranch();
builder.insertOutcome(3);
int depth = machine.getDepth();
int result = machine.decide(9);

// ===================================== //

BDTree<int, int> machine = ArrayBDTree<>(2);
BDTreeBuilder<int, int> builder = machine.builder();
builder.insertCondition(a -> a > 10);
builder.goToTrueBranch();
builder.insertOutcome(1);
builder.goBack();
builder.goToFalseBranch();
builder.insertCondition(a -> a < 5);
builder.goToTrueBranch();
builder.insertOutcome(2);
builder.goBack();
builder.goToFalseBranch();
builder.insertOutcome(3);
int depth = machine.getDepth();
int result = machine.decide(9);

// ===================================== //

BDTree<int, int> casino = new ArrayBDTree<>();
BDTreeBuilder<> builder = validator.builder();
builder.insertOutcome(a -> {
	Random random = new Random();
	if (Math.random() > 0.5) {
		a*=2;
	} else {
		a=0;
	}
	return a;
})

// ===================================== //

BDTree<int, int> casino = new ArrayBDTree<>();
BDTreeBuilder<> builder = validator.builder();
builder.insertCondition(a -> {
	Random random = new Random();
	if (Math.random() > 0.5) {
		a*=2;
		return true;
	}
	return false;
})
builder.goToTrueBranch();
builder.insertOutcome(a -> {
	Random random = new Random();
	if (Math.random() > 0.5) {
		a*=2;
	} else {
		a=0;
	}
	return a;
})
builder.goBack();
builder.goToFalseBranch();
builder.insertOutcome(0);

// ===================================== //

interface User {
	public int getAge();
	public int getBalance();
}

enum AdvertisementType {
	GENERAL,
	RICH,
	CHILD
}

BDTree<User, AdvertisementTypeA> tree = new DynamicBDTree<>();
BDTreeBuilder<> builder = tree.builder();
builder.insertCondition(user -> {
	return user.getAge() > 18;
});
builder.goToTrueBranch();
builder.insertCondition(user -> {
	return user.getBalance() > 10000;
});
builder.goToTrueBranch();
builder.insertOutcome(AdvertisementType.RICH);
builder.goBack();
builder.goToFalseBranch();
builder.insertOutcome(AdvertisementType.GENERAL);
builder.goBack();
builder.goBack();
builder.goToFalseBranch();
builder.insertOutcome(AdvertisementType.CHILD);
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

# java-decision-tree
## Classes
### CBDTreeNode
The base class for nodes (vertices) of binary tree. It has the level (integer) (e.g. level = 0 means that it is root of tree). 


Prototype of usage 
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

Notes
---
- rename library to BinDecisTree (bindecistree)

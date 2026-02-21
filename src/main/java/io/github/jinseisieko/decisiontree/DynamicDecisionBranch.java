package io.github.jinseisieko.decisiontree;

class DynamicDecisionBranch<I, O> implements DecisionBranch<I, O> {
    private final DynamicDecisionTree<I, O>.Builder builder;
    private final AbstractDecisionNode<I, O> rootNode;

    DynamicDecisionBranch(DynamicDecisionTree<I, O>.Builder builder, AbstractDecisionNode<I, O> rootNode) {
        this.builder = builder;
        this.rootNode = rootNode;
    }

    AbstractDecisionNode<I, O> getRootNode() {
        return rootNode;
    }

    @Override
    public DecisionBranch<I, O> getTrueBranch() {
        if (!(rootNode instanceof ConditionNode)) {
            throw new IllegalStateException("Cannot get true branch: current node is not a condition node");
        }
        AbstractDecisionNode<I, O> trueChild = rootNode.getTrueChild();
        if (trueChild == null) {
            throw new IllegalStateException("Cannot get true branch: true child does not exist");
        }
        return new DynamicDecisionBranch<>(builder, trueChild);
    }

    @Override
    public DecisionBranch<I, O> getFalseBranch() {
        if (!(rootNode instanceof ConditionNode)) {
            throw new IllegalStateException("Cannot get false branch: current node is not a condition node");
        }
        AbstractDecisionNode<I, O> falseChild = rootNode.getFalseChild();
        if (falseChild == null) {
            throw new IllegalStateException("Cannot get false branch: false child does not exist");
        }
        return new DynamicDecisionBranch<>(builder, falseChild);
    }

    @Override
    public int getDepth() {
        return calculateDepth(rootNode);
    }

    private int calculateDepth(AbstractDecisionNode<I, O> node) {
        if (node == null) {
            return -1;
        }
        if (node instanceof OutcomeNode) {
            return 0;
        }
        int trueDepth = calculateDepth(node.getTrueChild());
        int falseDepth = calculateDepth(node.getFalseChild());
        return 1 + Math.max(trueDepth, falseDepth);
    }
}

package io.github.jinseisieko.bindecistree;

abstract class DynamicBinDecisTreeNode<D, V> {
    private DynamicBinDecisTreeNode<D, V> trueNode;
    private DynamicBinDecisTreeNode<D, V> falseNode;

    abstract public boolean isComplete();

    abstract public V execute(D data);

    public DynamicBinDecisTreeBranch<D,V> toBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setTrueNode(DynamicBinDecisTreeNode<D, V> trueNode) {
        this.trueNode = trueNode;
    }

    public void setFalseNode(DynamicBinDecisTreeNode<D, V> falseNode) {
        this.falseNode = falseNode;
    }

    public DynamicBinDecisTreeNode<D, V> getTrueNode() {
        return trueNode;
    }

    public DynamicBinDecisTreeNode<D, V> getFalseNode() {
        return falseNode;
    }
    
    public void setAllNodes(DynamicBinDecisTreeNode<D, V> trueNode, DynamicBinDecisTreeNode<D, V> falseNode) {
        setTrueNode(trueNode);
        setFalseNode(falseNode);
    }
}
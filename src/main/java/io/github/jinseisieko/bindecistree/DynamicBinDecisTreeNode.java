package io.github.jinseisieko.bindecistree;

abstract class DynamicBinDecisTreeNode<D, V> {
    private DynamicBinDecisTreeNode<D, V> trueNode;
    private DynamicBinDecisTreeNode<D, V> falseNode;

    abstract public boolean isComplete();

    abstract public V execute(D data);

    public DynamicBinDecisTreeBranch<D,V> toBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
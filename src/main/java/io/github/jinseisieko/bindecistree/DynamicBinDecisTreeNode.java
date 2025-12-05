package io.github.jinseisieko.bindecistree;
import java.util.Objects;

abstract class DynamicBinDecisTreeNode<D, V> {
    private DynamicBinDecisTreeNode<D, V> trueNode;
    private DynamicBinDecisTreeNode<D, V> falseNode;

    abstract public boolean isComplete();

    abstract public V execute(D data);

    public void setTrueNode(DynamicBinDecisTreeNode<D, V> trueNode) {
        this.trueNode = Objects.requireNonNull(trueNode, "Node cannot be null");
    }

    public void setFalseNode(DynamicBinDecisTreeNode<D, V> falseNode) {
        this.falseNode = Objects.requireNonNull(falseNode, "Node cannot be null");;
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

    abstract public DynamicBinDecisTreeNode<D, V> copy();
}
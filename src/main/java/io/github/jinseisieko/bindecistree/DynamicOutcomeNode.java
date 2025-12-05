package io.github.jinseisieko.bindecistree;

import java.util.Objects;
import java.util.function.Function;

final class DynamicOutcomeNode<D, V> extends DynamicBinDecisTreeNode<D, V> {
    private Function<D, V> handler;
    
    public DynamicOutcomeNode(Function<D, V> handler) {
        this.setHandler(handler);
    }

    public DynamicOutcomeNode(V value) {
        this.setHandler(value);
    }

    private DynamicOutcomeNode(DynamicOutcomeNode<D,V> node) {
        this.handler = node.handler;
    }

    public void setHandler(Function<D, V> handler) {
        this.handler = Objects.requireNonNull(handler, "Handler cannot be null");
    }

    public void setHandler(V value) {
        this.handler = (data) -> Objects.requireNonNull(value, "Value cannot be null");;
    }

    @Override
    public V execute(D data) {
        Objects.requireNonNull(data, "Data cannot be null");
        return handler.apply(data);
    }

    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public DynamicBinDecisTreeNode<D, V> copy() {
        return new DynamicOutcomeNode<>(this);
    }

    @Override
    public void setTrueNode(DynamicBinDecisTreeNode<D,V> trueNode) {
        Objects.requireNonNull(trueNode, "Node cannot be null");
        throw new UnsupportedOperationException("Outcome nodes cannot have children");
    }

    @Override
    public void setFalseNode(DynamicBinDecisTreeNode<D,V> falseNode) {
        Objects.requireNonNull(falseNode, "Node cannot be null");
        throw new UnsupportedOperationException("Outcome nodes cannot have children");
    }

    @Override
    public void setAllNodes(DynamicBinDecisTreeNode<D,V> trueNode, DynamicBinDecisTreeNode<D,V> falseNode) {
        Objects.requireNonNull(trueNode, "Node cannot be null");
        Objects.requireNonNull(falseNode, "Node cannot be null");
        throw new UnsupportedOperationException("Outcome nodes cannot have children");
    }
}
package io.github.jinseisieko.bindecistree;

import java.util.Objects;
import java.util.function.Predicate;

final class DynamicConditionNode<D,V> extends DynamicBinDecisTreeNode<D, V> {
    private Predicate<D> condition;

    public DynamicConditionNode(Predicate<D> condition) {
        this.setCondition(condition);
    }

    private DynamicConditionNode(DynamicConditionNode<D,V> node) {
        this.condition = node.condition;
    }

    public void setCondition(Predicate<D> condition) {
        this.condition = Objects.requireNonNull(condition, "Condition cannot be null");;
    }

    @Override
    public V execute(D data) {
        Objects.requireNonNull(data, "Data cannot be null");
        if (getTrueNode() == null || getFalseNode() == null) {
            throw new IllegalStateException("Node cannot be executed while while at least one of the children is null");
        }
        if (this.condition.test(data)) {
            return getTrueNode().execute(data);
        } else {
            return getFalseNode().execute(data);
        }
    }

    @Override
    public boolean isComplete() {
        return this.getTrueNode() != null
            && this.getFalseNode() != null;
    }

    @Override
    public DynamicBinDecisTreeNode<D, V> copy() {
        return new DynamicConditionNode<>(this);
    }
}
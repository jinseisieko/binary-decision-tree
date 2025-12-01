package io.github.jinseisieko.bindecistree;

import java.util.function.Predicate;

class DynamicConditionNode<D,V> extends DynamicBinDecisTreeNode<D, V> {
    private Predicate<D> condition;

    public void setCondition(Predicate<D> condition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public V execute(D data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
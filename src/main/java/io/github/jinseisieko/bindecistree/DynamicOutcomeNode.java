package io.github.jinseisieko.bindecistree;

import java.util.function.Function;

class DynamicOutcomeNode<D, V> extends DynamicBinDecisTreeNode<D, V> {
    private Function<D, V> handler;
    
    public void setHandler(Function<D, V> handler) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setHandler(V value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public V execute(D data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
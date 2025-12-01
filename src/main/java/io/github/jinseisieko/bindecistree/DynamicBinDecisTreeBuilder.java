package io.github.jinseisieko.bindecistree;

import java.util.function.Function;
import java.util.function.Predicate;

public class DynamicBinDecisTreeBuilder<D,V> implements BinDecisTreeBuilder<D, V> {
    private DynamicBinDecisTree<D,V> tree;
    private int depth;
    private DynamicBinDecisTreeNode<D,V> node;

    public DynamicBinDecisTreeBuilder(DynamicBinDecisTree<D,V> tree) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void inserCondition(Predicate<D> condition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertOutcome(Function<D, V> handler) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertOutcome(V value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void goToTrueBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void goToFalseBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void goToNeighborBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void goBack() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clearBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertBranch(BinDecisTreeBranch<D, V> branch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BinDecisTreeBranch<D, V> toBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDepth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
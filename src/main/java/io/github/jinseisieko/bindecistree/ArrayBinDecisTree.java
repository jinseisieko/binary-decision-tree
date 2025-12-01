package io.github.jinseisieko.bindecistree;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayBinDecisTree<D,V> implements BinDecisTree<D, V>{
    private final int DEPTH;
    private Predicate<D>[] nodes;
    private Function<D, V>[] outcomes;

    public ArrayBinDecisTree(int depth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayBinDecisTree(int depth, BinDecisTreeBranch<D,V> branch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public V decide(D data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isFull() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDepth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BinDecisTreeBuilder<D, V> builder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BinDecisTreeBranch<D, V> toBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<BinDecisTreeBranch<D, V>> getAllBranches() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<BinDecisTreeBranch<D, V>> getAllBranchesWithDepth(int depth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
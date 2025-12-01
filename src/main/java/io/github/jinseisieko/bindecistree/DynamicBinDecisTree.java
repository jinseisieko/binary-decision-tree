package io.github.jinseisieko.bindecistree;

import java.util.List;

public class DynamicBinDecisTree<D,V> implements BinDecisTree<D, V> {
    private int depth;
    private DynamicBinDecisTreeNode<D, V> root;

    public DynamicBinDecisTree() {
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
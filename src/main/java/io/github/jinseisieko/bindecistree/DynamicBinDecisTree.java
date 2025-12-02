package io.github.jinseisieko.bindecistree;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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

    public class DynamicBinDecisTreeBuilder<D,V> implements BinDecisTreeBuilder<D, V> {
        private DynamicBinDecisTree<D,V> tree;
        private int depth;
        private DynamicBinDecisTreeNode<D,V> node;

        public DynamicBinDecisTreeBuilder(DynamicBinDecisTree<D,V> tree) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> inserCondition(Predicate<D> condition) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> insertOutcome(Function<D, V> handler) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> insertOutcome(V value) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> goToTrueBranch() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> goToFalseBranch() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> goToNeighborBranch() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> goBack() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> clear() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> clearBranch() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> insertBranch(BinDecisTreeBranch<D, V> branch) {
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
}
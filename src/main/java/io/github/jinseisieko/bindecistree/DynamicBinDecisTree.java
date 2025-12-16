package io.github.jinseisieko.bindecistree;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class DynamicBinDecisTree<D,V> implements BinDecisTree<D, V> {
    private int depth;
    private DynamicBinDecisTreeNode<D, V> root;

    private boolean builded = false; 
    private boolean isComplete = false;

    public DynamicBinDecisTree() {
        this.depth = -1;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public V decide(D data) {
        if (!builded) {
            throw new IllegalStateException("Unbuilded tree cannot decide");
        }
        return root.execute(Objects.requireNonNull(data, "Data cannot be null"));
    }

    @Override
    public boolean isComplete() {
        if (!builded) {
            throw new IllegalStateException("Unbuilded tree cannot have completeness");
        }
        return isComplete;
    }

    @Override
    public int getDepth() {
        if (!builded) {
            throw new IllegalStateException("Unbuilded tree cannot have depth");
        }
        return depth;    
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

    @Override
    public BinDecisTree<D, V> copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class DynamicBinDecisTreeBuilder<D,V> implements BinDecisTreeBuilder<D, V> {
        private DynamicBinDecisTree<D,V> tree;
        private int depth;
        private DynamicBinDecisTreeNode<D,V> node;


        public DynamicBinDecisTreeBuilder(DynamicBinDecisTree<D,V> tree) {
            this.tree = (DynamicBinDecisTree<D,V>) tree.copy();
            this.depth = this.tree.depth;
            this.node = this.tree.root;
        }

        @Override
        public BinDecisTreeBuilder<D,V> insertCondition(Predicate<D> condition) {
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
        public BinDecisTreeBuilder<D,V> goToTrueNode() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> goToFalseNode() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> goToSiblingNode() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinDecisTreeBuilder<D,V> goToRoot() {
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

        @Override
        public BinDecisTree<D, V> build() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
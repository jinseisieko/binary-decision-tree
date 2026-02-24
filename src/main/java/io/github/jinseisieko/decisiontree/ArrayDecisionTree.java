package io.github.jinseisieko.decisiontree;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Array-backed implementation of a binary decision tree.
 */
public class ArrayDecisionTree<I, O> implements BinaryDecisionTree<I, O> {
    private final int maxDepth;
    private Predicate<I>[] conditions;
    private Function<I, O>[] outcomes;

    public ArrayDecisionTree(int maxDepth) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayDecisionTree(int maxDepth, DecisionBranch<I, O> branch) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public O decide(I input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DecisionTreeBuilder<I, O> builder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BinaryDecisionTree<I, O> copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDepth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isFullyDefined() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class Builder implements DecisionTreeBuilder<I, O> {
        private ArrayDecisionTree<I, O> tree;
        private int depth;

        public Builder(ArrayDecisionTree<I, O> tree) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> insertCondition(Predicate<I> condition) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> insertOutcome(Function<I, O> handler) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> insertOutcome(O value) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> goToTrueChild() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> goToFalseChild() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> goToSibling() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> goToRoot() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> goToParent() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> clearTree() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> clearSubtree() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionTreeBuilder<I, O> insertBranch(DecisionBranch<I, O> branch) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionBranch<I, O> asBranch() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DecisionBranch<I, O> asBranchOfSubtree() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<DecisionBranch<I, O>> getAllBranches() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public List<DecisionBranch<I, O>> getAllBranchesWithDepth(int depth) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getDepth() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isComplete() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean isFullyDefined() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public BinaryDecisionTree<I, O> build() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        
    }
}

package io.github.jinseisieko.decisiontree;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class DynamicDecisionTree<I, O> implements BinaryDecisionTree<I, O> {
    private int depth;
    private AbstractDecisionNode<I, O> root;

    private boolean fullyDefined = false;

    public DynamicDecisionTree() {
        this.depth = -1;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public O decide(I input) {
        if (!fullyDefined) {
            throw new IllegalStateException("Unbuilt tree cannot decide");
        }
        return root.execute(Objects.requireNonNull(input, "Input cannot be null"));
    }

    @Override
    public DecisionTreeBuilder<I, O> builder() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BinaryDecisionTree<I, O> copy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class Builder implements DecisionTreeBuilder<I, O> {
        private DynamicDecisionTree<I, O> tree;
        private int depth;
        private AbstractDecisionNode<I, O> node;

        public Builder(DynamicDecisionTree<I, O> tree) {
            this.tree = (DynamicDecisionTree<I, O>) tree.copy();
            this.depth = this.tree.depth;
            this.node = this.tree.root;
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
        public DecisionBranch<I, O> asBranchoOfSubtree() {
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

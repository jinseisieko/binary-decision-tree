package io.github.jinseisieko.decisiontree;

import java.util.ArrayList;
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
        this.root = null;
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
        return new Builder(this);
    }

    @Override
    public BinaryDecisionTree<I, O> copy() {
        DynamicDecisionTree<I, O> copy = new DynamicDecisionTree<>();
        copy.depth = this.depth;
        copy.fullyDefined = this.fullyDefined;
        if (this.root != null) {
            copy.root = this.root.copy();
        }
        return copy;
    }

    @Override
    public int getDepth() {
        if (root == null) {
            throw new IllegalStateException("Cannot get depth: tree is empty");
        }
        return depth;
    }

    @Override
    public boolean isFullyDefined() {
        if (root == null) {
            throw new IllegalStateException("Cannot check if fully defined: tree is empty");
        }
        return fullyDefined;
    }

    int getDepthInternal() {
        return depth;
    }

    void setDepthInternal(int depth) {
        this.depth = depth;
    }

    AbstractDecisionNode<I, O> getRootInternal() {
        return root;
    }

    void setRootInternal(AbstractDecisionNode<I, O> root) {
        this.root = root;
    }

    boolean isFullyDefinedInternal() {
        return fullyDefined;
    }

    void setFullyDefinedInternal(boolean fullyDefined) {
        this.fullyDefined = fullyDefined;
    }

    public class Builder implements DecisionTreeBuilder<I, O> {
        private DynamicDecisionTree<I, O> tree;
        private AbstractDecisionNode<I, O> currentNode;

        public Builder(DynamicDecisionTree<I, O> tree) {
            this.tree = tree;
            if (tree.root != null) {
                this.currentNode = tree.root.copy();
            } else {
                this.currentNode = null;
            }
        }

        private Builder(DynamicDecisionTree<I, O> tree, AbstractDecisionNode<I, O> node) {
            this.tree = tree;
            this.currentNode = node;
        }

        @Override
        public DecisionTreeBuilder<I, O> insertCondition(Predicate<I> condition) {
            Objects.requireNonNull(condition, "Condition cannot be null");
            ConditionNode<I, O> newNode = new ConditionNode<>(condition);
            replaceCurrentNode(newNode);
            return this;
        }

        @Override
        public DecisionTreeBuilder<I, O> insertOutcome(Function<I, O> handler) {
            Objects.requireNonNull(handler, "Handler cannot be null");
            OutcomeNode<I, O> newNode = new OutcomeNode<>(handler);
            replaceCurrentNode(newNode);
            return this;
        }

        @Override
        public DecisionTreeBuilder<I, O> insertOutcome(O value) {
            Objects.requireNonNull(value, "Value cannot be null");
            OutcomeNode<I, O> newNode = new OutcomeNode<>(value);
            replaceCurrentNode(newNode);
            return this;
        }

        private void replaceCurrentNode(AbstractDecisionNode<I, O> newNode) {
            if (currentNode == null) {
                tree.root = newNode;
                currentNode = newNode;
            } else {
                AbstractDecisionNode<I, O> parent = findParent(tree.root, currentNode);
                if (parent == null) {
                    tree.root = newNode;
                } else if (parent.getTrueChild() == currentNode) {
                    parent.setTrueChild(newNode);
                } else {
                    parent.setFalseChild(newNode);
                }
                currentNode = newNode;
            }
        }

        private AbstractDecisionNode<I, O> findParent(AbstractDecisionNode<I, O> root,
                                                       AbstractDecisionNode<I, O> child) {
            if (root == null || child == tree.root) {
                return null;
            }
            if (root.getTrueChild() == child || root.getFalseChild() == child) {
                return root;
            }
            AbstractDecisionNode<I, O> found = findParent(root.getTrueChild(), child);
            if (found != null) {
                return found;
            }
            return findParent(root.getFalseChild(), child);
        }

        @Override
        public DecisionTreeBuilder<I, O> goToTrueChild() {
            if (currentNode == null) {
                throw new IllegalStateException("Cannot navigate: tree is empty");
            }
            if (!(currentNode instanceof ConditionNode)) {
                throw new IllegalStateException("Cannot navigate: current node is not a condition node");
            }
            AbstractDecisionNode<I, O> trueChild = currentNode.getTrueChild();
            if (trueChild == null) {
                trueChild = new OutcomeNode<>(input -> {
                    throw new IllegalStateException("Reached unbuilt outcome node");
                });
                currentNode.setTrueChild(trueChild);
            }
            currentNode = trueChild;
            return this;
        }

        @Override
        public DecisionTreeBuilder<I, O> goToFalseChild() {
            if (currentNode == null) {
                throw new IllegalStateException("Cannot navigate: tree is empty");
            }
            if (!(currentNode instanceof ConditionNode)) {
                throw new IllegalStateException("Cannot navigate: current node is not a condition node");
            }
            AbstractDecisionNode<I, O> falseChild = currentNode.getFalseChild();
            if (falseChild == null) {
                falseChild = new OutcomeNode<>(input -> {
                    throw new IllegalStateException("Reached unbuilt outcome node");
                });
                currentNode.setFalseChild(falseChild);
            }
            currentNode = falseChild;
            return this;
        }

        @Override
        public DecisionTreeBuilder<I, O> goToSibling() {
            if (currentNode == null || currentNode == tree.root) {
                throw new IllegalStateException("Cannot navigate: no sibling exists");
            }
            AbstractDecisionNode<I, O> parent = findParent(tree.root, currentNode);
            if (parent == null) {
                throw new IllegalStateException("Cannot navigate: no sibling exists");
            }
            if (parent.getTrueChild() == currentNode) {
                AbstractDecisionNode<I, O> falseChild = parent.getFalseChild();
                if (falseChild == null) {
                    falseChild = new OutcomeNode<>(input -> {
                        throw new IllegalStateException("Reached unbuilt outcome node");
                    });
                    parent.setFalseChild(falseChild);
                }
                currentNode = falseChild;
            } else {
                AbstractDecisionNode<I, O> trueChild = parent.getTrueChild();
                if (trueChild == null) {
                    trueChild = new OutcomeNode<>(input -> {
                        throw new IllegalStateException("Reached unbuilt outcome node");
                    });
                    parent.setTrueChild(trueChild);
                }
                currentNode = trueChild;
            }
            return this;
        }

        @Override
        public DecisionTreeBuilder<I, O> goToRoot() {
            currentNode = tree.root;
            return this;
        }

        @Override
        public DecisionTreeBuilder<I, O> goToParent() {
            if (currentNode == null || currentNode == tree.root) {
                throw new IllegalStateException("Cannot navigate: no parent exists");
            }
            AbstractDecisionNode<I, O> parent = findParent(tree.root, currentNode);
            if (parent == null) {
                throw new IllegalStateException("Cannot navigate: no parent exists");
            }
            currentNode = parent;
            return this;
        }

        @Override
        public DecisionTreeBuilder<I, O> clearTree() {
            tree.root = null;
            currentNode = null;
            tree.depth = -1;
            tree.fullyDefined = false;
            return this;
        }

        @Override
        public DecisionTreeBuilder<I, O> clearSubtree() {
            if (currentNode == null) {
                return this;
            }
            if (currentNode instanceof ConditionNode) {
                currentNode.clearChildren();
            }
            return this;
        }

        @Override
        public DecisionTreeBuilder<I, O> insertBranch(DecisionBranch<I, O> branch) {
            Objects.requireNonNull(branch, "Branch cannot be null");
            if (!(branch instanceof DynamicDecisionBranch)) {
                throw new IllegalArgumentException("Branch must be a DynamicDecisionBranch");
            }
            DynamicDecisionBranch<I, O> dynamicBranch = (DynamicDecisionBranch<I, O>) branch;
            AbstractDecisionNode<I, O> branchNode = dynamicBranch.getRootNode().copy();
            if (currentNode == null) {
                tree.root = branchNode;
                currentNode = branchNode;
            } else {
                AbstractDecisionNode<I, O> parent = findParent(tree.root, currentNode);
                if (parent == null) {
                    tree.root = branchNode;
                } else if (parent.getTrueChild() == currentNode) {
                    parent.setTrueChild(branchNode);
                } else {
                    parent.setFalseChild(branchNode);
                }
                currentNode = branchNode;
            }
            return this;
        }

        @Override
        public DecisionBranch<I, O> asBranch() {
            if (currentNode == null) {
                throw new IllegalStateException("Cannot create branch: current node is null");
            }
            return new DynamicDecisionBranch<>(this, currentNode);
        }

        @Override
        public DecisionBranch<I, O> asBranchOfSubtree() {
            if (currentNode == null) {
                throw new IllegalStateException("Cannot create branch: current node is null");
            }
            AbstractDecisionNode<I, O> copiedNode = currentNode.copy();
            return new DynamicDecisionBranch<>(new Builder(tree, copiedNode), copiedNode);
        }

        @Override
        public List<DecisionBranch<I, O>> getAllBranches() {
            List<DecisionBranch<I, O>> branches = new ArrayList<>();
            if (tree.root != null) {
                collectAllBranches(tree.root, branches);
            }
            return branches;
        }

        private void collectAllBranches(AbstractDecisionNode<I, O> node,
                                        List<DecisionBranch<I, O>> branches) {
            if (node == null) {
                return;
            }
            branches.add(new DynamicDecisionBranch<>(this, node));
            if (node instanceof ConditionNode) {
                collectAllBranches(node.getTrueChild(), branches);
                collectAllBranches(node.getFalseChild(), branches);
            }
        }

        @Override
        public List<DecisionBranch<I, O>> getAllBranchesWithDepth(int depth) {
            if (depth < 0) {
                throw new IllegalArgumentException("Depth cannot be negative");
            }
            List<DecisionBranch<I, O>> branches = new ArrayList<>();
            if (tree.root != null) {
                collectBranchesAtDepth(tree.root, 0, depth, branches);
            }
            return branches;
        }

        private void collectBranchesAtDepth(AbstractDecisionNode<I, O> node,
                                            int currentDepth,
                                            int targetDepth,
                                            List<DecisionBranch<I, O>> branches) {
            if (node == null) {
                return;
            }
            if (currentDepth == targetDepth) {
                branches.add(new DynamicDecisionBranch<>(this, node));
                return;
            }
            if (node instanceof ConditionNode) {
                collectBranchesAtDepth(node.getTrueChild(), currentDepth + 1, targetDepth, branches);
                collectBranchesAtDepth(node.getFalseChild(), currentDepth + 1, targetDepth, branches);
            }
        }

        @Override
        public int getDepth() {
            if (tree.root == null) {
                throw new IllegalStateException("Cannot get depth: tree is empty");
            }
            return calculateDepth(tree.root);
        }

        private int calculateDepth(AbstractDecisionNode<I, O> node) {
            if (node == null) {
                return -1;
            }
            if (node instanceof OutcomeNode) {
                return 0;
            }
            int trueDepth = calculateDepth(node.getTrueChild());
            int falseDepth = calculateDepth(node.getFalseChild());
            return 1 + Math.max(trueDepth, falseDepth);
        }

        @Override
        public boolean isComplete() {
            if (tree.root == null) {
                return false;
            }
            return isNodeComplete(tree.root);
        }

        private boolean isNodeComplete(AbstractDecisionNode<I, O> node) {
            if (node == null) {
                return false;
            }
            return node.isComplete();
        }

        @Override
        public boolean isFullyDefined() {
            if (tree.root == null) {
                throw new IllegalStateException("Cannot check if fully defined: tree is empty");
            }
            int depth = calculateDepth(tree.root);
            return checkAllPathsHaveDepth(tree.root, 0, depth);
        }

        private boolean checkAllPathsHaveDepth(AbstractDecisionNode<I, O> node, int currentDepth, int targetDepth) {
            if (node == null) {
                return false;
            }
            if (node instanceof OutcomeNode) {
                return currentDepth == targetDepth;
            }
            if (node instanceof ConditionNode) {
                return checkAllPathsHaveDepth(node.getTrueChild(), currentDepth + 1, targetDepth)
                    && checkAllPathsHaveDepth(node.getFalseChild(), currentDepth + 1, targetDepth);
            }
            return false;
        }

        @Override
        public BinaryDecisionTree<I, O> build() {
            if (tree.root == null) {
                throw new IllegalStateException("Cannot build: tree is empty");
            }
            if (!isComplete()) {
                throw new IllegalStateException("Cannot build: tree is incomplete");
            }
            tree.depth = calculateDepth(tree.root);
            tree.fullyDefined = isFullyDefined();
            tree.root = currentNode = tree.root.copy();
            return tree;
        }
    }
}

package io.github.jinseisieko.decisiontree;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Provides a step-by-step builder for constructing and modifying a binary decision tree.
 *
 * <p>This builder enables controlled navigation through the tree structure, allowing the user 
 * to insert conditions and outcomes.
 *
 * @param <I> the type of the input value
 * @param <O> the type of the output value
 *
 * @author jinseisieko
 */
public interface DecisionTreeBuilder<I, O> {

    /**
     * Replaces the current node with a new conditional node defined by the specified predicate.
     *
     * <p>The new node requires the explicit insertion of both child branches.
     *
     * @param condition the predicate determining branch selection based on the input value {@code I}
     * @return this builder instance
     */
    DecisionTreeBuilder<I, O> insertCondition(Predicate<I> condition);

    DecisionTreeBuilder<I, O> insertOutcome(Function<I, O> handler);

    DecisionTreeBuilder<I, O> insertOutcome(O value);

    DecisionTreeBuilder<I, O> goToTrueChild();

    DecisionTreeBuilder<I, O> goToFalseChild();

    DecisionTreeBuilder<I, O> goToSibling();

    DecisionTreeBuilder<I, O> goToRoot();

    DecisionTreeBuilder<I, O> goToParent();

    DecisionTreeBuilder<I, O> clearTree();

    DecisionTreeBuilder<I, O> clearSubtree();

    DecisionTreeBuilder<I, O> insertBranch(DecisionBranch<I, O> branch);

    DecisionBranch<I, O> asBranch();

    DecisionBranch<I, O> asBranchoOfSubtree();

    List<DecisionBranch<I,O>> getAllBranches();

    List<DecisionBranch<I,O>> getAllBranchesWithDepth(int depth);

    int getDepth();

    boolean isComplete();

    boolean isFullyDefined();

    BinaryDecisionTree<I, O> build();
}

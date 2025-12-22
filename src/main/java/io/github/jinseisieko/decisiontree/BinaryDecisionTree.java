package io.github.jinseisieko.decisiontree;

import java.util.List;

/**
 * Represents a binary decision tree that determines an output value of type {@code O}
 * from a given input value of type {@code I}.
 * 
 * <p>Use the {@code DecisionTreeBuilder} instance returned by the {@code builder()} method to construct and modify a tree.
 *
 * <p>Each instance defines branching logic and corresponding leaf decisions that produce
 * outcomes based on the evaluated input.
 *
 * @param <I> the type of the input value evaluated by the tree
 * @param <O> the type of the output value produced by the tree
 *
 * @author jinseisieko
 */
public interface BinaryDecisionTree<I, O> {

    O decide(I input);

    boolean isFullyDefined();

    int getDepth();

    DecisionTreeBuilder<I, O> builder();

    DecisionBranch<I, O> asBranch();

    List<DecisionBranch<I, O>> getAllBranches();

    List<DecisionBranch<I, O>> getAllBranchesWithDepth(int depth);

    BinaryDecisionTree<I, O> copy();
}

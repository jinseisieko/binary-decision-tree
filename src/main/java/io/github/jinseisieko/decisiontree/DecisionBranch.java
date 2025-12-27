package io.github.jinseisieko.decisiontree;

/**
 * Represents a subtree rooted at some node within an existing binary decision tree.
 * 
 * <p>This interface provides a reference to a specific section of the tree rather than
 * storing independent data.
 * 
 * @param <I> the type of the input value
 * @param <O> the type of the output value
 * 
 * @author jinseisieko
 */
public interface DecisionBranch<I, O> {

    /**
     * Detaches the false branch and returns the subtree rooted at the true node.
     *
     * @return the {@code DecisionBranch} representing the subtree rooted at the true node
     */
    DecisionBranch<I, O> getTrueBranch();

    /**
     * Detaches the true branch and returns the subtree rooted at the false node.
     *
     * @return the {@code DecisionBranch} representing the subtree rooted at the false node
     */
    DecisionBranch<I, O> getFalseBranch();

    /**
     * Returns the number of edges in the longest path form root to leaf.
     * 
     * @return the depth of the binary decision tree
     */
    int getDepth();
}

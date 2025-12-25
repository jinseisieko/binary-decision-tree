package io.github.jinseisieko.decisiontree;

/**
 * Represents a subtree rooted at some vertex within an existing binary decision tree.
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

    DecisionBranch<I, O> getTrueBranch();

    DecisionBranch<I, O> getFalseBranch();

    int getDepth();
}

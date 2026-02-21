package io.github.jinseisieko.decisiontree;

/**
 * Represents a binary decision tree that determines an output value of type {@code O}
 * from a given input value of type {@code I}.
 * 
 * <p>Use the {@code DecisionTreeBuilder} instance returned by the {@code builder()}
 *  method to construct and modify a tree.
 *
 * <p>Each instance defines branching logic and corresponding leaf decisions that produce
 * outcomes based on the input value and conditions in internal nodes.
 *
 * @param <I> the type of the input value
 * @param <O> the type of the output value produced by the tree decision
 *
 * @author jinseisieko
 */
public interface BinaryDecisionTree<I, O> {

    /**
     * Decides and returns value of type {@code O} based on input value of type {@code I}
     * and conditions in tree.
     * 
     * @param input the input value of type {@code I}.
     * @return the output value determined by the tree.
     */
    O decide(I input);

    /**
     * Returns a {@code DecisionTreeBuilder} instance that operates on a copy of this tree,
     * allowing modification and reconstruction into a new, updated tree.
     *
     * @return a {@code DecisionTreeBuilder} for modifying a copy of this tree
     */
    DecisionTreeBuilder<I, O> builder();

    /**
     * Creates and returns a deep copy of this binary decision tree
     *
     * @return a new {@code BinaryDecisionTree} instance identical in structure
     */
    BinaryDecisionTree<I, O> copy();

    /**
     * Returns the depth of the tree (the number of edges in the longest path from root to leaf).
     *
     * @return the depth of the tree
     * @throws IllegalStateException if the tree is empty
     */
    int getDepth();

    /**
     * Checks whether the tree is fully defined (all branches lead to outcomes).
     *
     * @return true if the tree is fully defined, false otherwise
     * @throws IllegalStateException if the tree is empty
     */
    boolean isFullyDefined();
}

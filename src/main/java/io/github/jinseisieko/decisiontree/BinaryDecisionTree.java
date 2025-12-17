package io.github.jinseisieko.decisiontree;

import java.util.List;

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

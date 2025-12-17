package io.github.jinseisieko.decisiontree;

public interface DecisionBranch<I, O> {

    DecisionBranch<I, O> getTrueBranch();

    DecisionBranch<I, O> getFalseBranch();

    int getDepth();
}

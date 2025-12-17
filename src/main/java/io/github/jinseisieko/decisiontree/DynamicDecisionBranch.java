package io.github.jinseisieko.decisiontree;

class DynamicDecisionBranch<I, O> implements DecisionBranch<I, O> {

    @Override
    public DecisionBranch<I, O> getTrueBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DecisionBranch<I, O> getFalseBranch() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDepth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

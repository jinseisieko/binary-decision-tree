package io.github.jinseisieko.decisiontree;

import java.util.Objects;

abstract class AbstractDecisionNode<I, O> {
    private AbstractDecisionNode<I, O> trueChild;
    private AbstractDecisionNode<I, O> falseChild;

    public abstract boolean isComplete();

    public abstract O execute(I input);

    public void setTrueChild(AbstractDecisionNode<I, O> trueChild) {
        this.trueChild = Objects.requireNonNull(trueChild, "Node cannot be null");
    }

    public void setFalseChild(AbstractDecisionNode<I, O> falseChild) {
        this.falseChild = Objects.requireNonNull(falseChild, "Node cannot be null");
    }

    public AbstractDecisionNode<I, O> getTrueChild() {
        return trueChild;
    }

    public AbstractDecisionNode<I, O> getFalseChild() {
        return falseChild;
    }

    public void setChildren(AbstractDecisionNode<I, O> trueChild, AbstractDecisionNode<I, O> falseChild) {
        setTrueChild(trueChild);
        setFalseChild(falseChild);
    }

    public abstract AbstractDecisionNode<I, O> copy();
}

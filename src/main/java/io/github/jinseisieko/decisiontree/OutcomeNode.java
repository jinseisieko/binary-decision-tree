package io.github.jinseisieko.decisiontree;

import java.util.Objects;
import java.util.function.Function;

final class OutcomeNode<I, O> extends AbstractDecisionNode<I, O> {
    private Function<I, O> handler;
    
    public OutcomeNode(Function<I, O> handler) {
        this.setHandler(handler);
    }

    public OutcomeNode(O value) {
        this.setHandler(value);
    }

    private OutcomeNode(OutcomeNode<I, O> node) {
        this.handler = node.handler;
    }

    public void setHandler(Function<I, O> handler) {
        this.handler = Objects.requireNonNull(handler, "Handler cannot be null");
    }

    public void setHandler(O value) {
        this.handler = input -> Objects.requireNonNull(value, "Value cannot be null");
    }

    @Override
    public O execute(I input) {
        Objects.requireNonNull(input, "Input cannot be null");
        return handler.apply(input);
    }

    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public AbstractDecisionNode<I, O> copy() {
        return new OutcomeNode<>(this);
    }

    @Override
    public void setTrueChild(AbstractDecisionNode<I, O> trueChild) {
        Objects.requireNonNull(trueChild, "Node cannot be null");
        throw new UnsupportedOperationException("Outcome nodes cannot have children");
    }

    @Override
    public void setFalseChild(AbstractDecisionNode<I, O> falseChild) {
        Objects.requireNonNull(falseChild, "Node cannot be null");
        throw new UnsupportedOperationException("Outcome nodes cannot have children");
    }

    @Override
    public void setChildren(AbstractDecisionNode<I, O> trueChild, AbstractDecisionNode<I, O> falseChild) {
        Objects.requireNonNull(trueChild, "Node cannot be null");
        Objects.requireNonNull(falseChild, "Node cannot be null");
        throw new UnsupportedOperationException("Outcome nodes cannot have children");
    }
}

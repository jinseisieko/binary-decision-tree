package io.github.jinseisieko.decisiontree;

import java.util.Objects;
import java.util.function.Predicate;

final class ConditionNode<I, O> extends AbstractDecisionNode<I, O> {
    private Predicate<I> condition;

    public ConditionNode(Predicate<I> condition) {
        this.setCondition(condition);
    }

    private ConditionNode(ConditionNode<I, O> node) {
        this.condition = node.condition;
    }

    public void setCondition(Predicate<I> condition) {
        this.condition = Objects.requireNonNull(condition, "Condition cannot be null");
    }

    @Override
    public O execute(I input) {
        Objects.requireNonNull(input, "Input cannot be null");
        if (getTrueChild() == null || getFalseChild() == null) {
            throw new IllegalStateException(
                "Node cannot be executed while at least one of the children is null"
            );
        }
        if (this.condition.test(input)) {
            return getTrueChild().execute(input);
        } else {
            return getFalseChild().execute(input);
        }
    }

    @Override
    public boolean isComplete() {
        return this.getTrueChild() != null
            && this.getFalseChild() != null;
    }

    @Override
    public AbstractDecisionNode<I, O> copy() {
        return new ConditionNode<>(this);
    }
}

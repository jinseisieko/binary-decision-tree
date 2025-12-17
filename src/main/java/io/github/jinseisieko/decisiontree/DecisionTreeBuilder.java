package io.github.jinseisieko.decisiontree;

import java.util.function.Function;
import java.util.function.Predicate;

public interface DecisionTreeBuilder<I, O> {

    DecisionTreeBuilder<I, O> insertCondition(Predicate<I> condition);

    DecisionTreeBuilder<I, O> insertOutcome(Function<I, O> handler);

    DecisionTreeBuilder<I, O> insertOutcome(O value);

    DecisionTreeBuilder<I, O> goToTrueChild();

    DecisionTreeBuilder<I, O> goToFalseChild();

    DecisionTreeBuilder<I, O> goToSibling();

    DecisionTreeBuilder<I, O> goToRoot();

    DecisionTreeBuilder<I, O> goToParent();

    DecisionTreeBuilder<I, O> clearTree();

    DecisionTreeBuilder<I, O> clearSubtree();

    DecisionTreeBuilder<I, O> insertBranch(DecisionBranch<I, O> branch);

    DecisionBranch<I, O> asBranch();

    int getDepth();

    BinaryDecisionTree<I, O> build();
}

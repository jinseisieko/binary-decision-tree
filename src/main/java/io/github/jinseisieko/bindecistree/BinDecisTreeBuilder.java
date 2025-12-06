package io.github.jinseisieko.bindecistree;

import java.util.function.Function;
import java.util.function.Predicate;


public interface BinDecisTreeBuilder<D,V> {

    public BinDecisTreeBuilder<D,V> insertCondition(Predicate<D> condition);

    public BinDecisTreeBuilder<D,V> insertOutcome(Function<D,V> handler);

    public BinDecisTreeBuilder<D,V> insertOutcome(V value);

    public BinDecisTreeBuilder<D,V> goToTrueNode();

    public BinDecisTreeBuilder<D,V> goToFalseNode();

    public BinDecisTreeBuilder<D,V> goToSiblingNode();

    public BinDecisTreeBuilder<D,V> goBack();

    public BinDecisTreeBuilder<D,V> clear();

    public BinDecisTreeBuilder<D,V> clearBranch();

    public BinDecisTreeBuilder<D,V> insertBranch(BinDecisTreeBranch<D,V> branch);

    public BinDecisTreeBranch<D,V> toBranch();

    public int getDepth();

    public BinDecisTree<D,V> build();
}
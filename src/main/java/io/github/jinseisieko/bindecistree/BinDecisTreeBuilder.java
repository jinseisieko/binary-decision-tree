package io.github.jinseisieko.bindecistree;

import java.util.function.Function;
import java.util.function.Predicate;


public interface BinDecisTreeBuilder<D,V> {

    public BinDecisTreeBuilder<D,V> inserCondition(Predicate<D> condition);

    public BinDecisTreeBuilder<D,V> insertOutcome(Function<D,V> handler);

    public BinDecisTreeBuilder<D,V> insertOutcome(V value);

    public BinDecisTreeBuilder<D,V> goToTrueBranch();

    public BinDecisTreeBuilder<D,V> goToFalseBranch();

    public BinDecisTreeBuilder<D,V> goToNeighborBranch();

    public BinDecisTreeBuilder<D,V> goBack();

    public BinDecisTreeBuilder<D,V> clear();

    public BinDecisTreeBuilder<D,V> clearBranch();

    public BinDecisTreeBuilder<D,V> insertBranch(BinDecisTreeBranch<D,V> branch);

    public BinDecisTreeBranch<D,V> toBranch();

    public int getDepth();

    public BinDecisTree<D,V> build();
}
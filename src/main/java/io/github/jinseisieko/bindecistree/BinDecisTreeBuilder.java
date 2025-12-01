package io.github.jinseisieko.bindecistree;

import java.util.function.Function;
import java.util.function.Predicate;


public interface BinDecisTreeBuilder<D,V> {

    public void inserCondition(Predicate<D> condition);

    public void insertOutcome(Function<D,V> handler);

    public void insertOutcome(V value);

    public void goToTrueBranch();

    public void goToFalseBranch();

    public void goToNeighborBranch();

    public void goBack();

    public void clear();

    public void clearBranch();

    public void insertBranch(BinDecisTreeBranch<D,V> branch);

    public BinDecisTreeBranch<D,V> toBranch();

    public int getDepth();
}
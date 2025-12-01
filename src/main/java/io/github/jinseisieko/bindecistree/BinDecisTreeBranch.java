package io.github.jinseisieko.bindecistree;

public interface BinDecisTreeBranch<D,V> {

    public BinDecisTreeBranch<D,V> getTrueBranch();

    public BinDecisTreeBranch<D,V> getFalseBranch();
    
    public int getDepth();
}
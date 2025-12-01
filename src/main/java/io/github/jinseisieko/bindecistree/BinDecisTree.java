package io.github.jinseisieko.bindecistree;

import java.util.List;

public interface BinDecisTree<D,V> {

    public V decide(D data);

    public boolean isFull();

    public int getDepth();

    public BinDecisTreeBuilder<D,V> builder();

    public BinDecisTreeBranch<D,V> toBranch();

    public List<BinDecisTreeBranch<D,V>> getAllBranches();
    
    public List<BinDecisTreeBranch<D,V>> getAllBranchesWithDepth(int depth);
}
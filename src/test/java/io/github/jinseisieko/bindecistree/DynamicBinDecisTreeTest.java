package io.github.jinseisieko.bindecistree;

import org.junit.jupiter.api.Test;

import static io.github.jinseisieko.bindecistree.TestUtilities.assertThrowsWithNonEmptyMessage;

class DynamicBinDecisTreeTest {
    @Test
    void decide_null_shouldThrowNullPointerException() {
        BinDecisTree<Integer, Integer> tree = new DynamicBinDecisTree<Integer, Integer>()
            .builder()
            .insertOutcome(0)
            .build();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> tree.decide(null));
    }

    @Test
    void decideUnbuildedTree_shouldThrowIllegalStateException() {
        BinDecisTree<Integer, Integer> tree = new DynamicBinDecisTree<>();
        assertThrowsWithNonEmptyMessage(IllegalStateException.class, () -> tree.decide(0));
    }
}
package io.github.jinseisieko.bindecistree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.github.jinseisieko.bindecistree.TestUtilities.alwaysTrue;
import static io.github.jinseisieko.bindecistree.TestUtilities.assertThrowsWithNonEmptyMessage;
import static io.github.jinseisieko.bindecistree.TestUtilities.buildCompleteSubtree;

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
    void decideOfUnbuildedTree_shouldThrowIllegalStateException() {
        BinDecisTree<Integer, Integer> tree = new DynamicBinDecisTree<>();
        assertThrowsWithNonEmptyMessage(IllegalStateException.class, () -> tree.decide(0));
    }

    @Test
    void isCompleteOfUnbuildedTree_shouldThrowIllegalStateException() {
        BinDecisTree<Integer, Integer> tree = new DynamicBinDecisTree<>();
        assertThrowsWithNonEmptyMessage(IllegalStateException.class, () -> tree.isComplete());

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void isCompleteOfCompleteTreeWithDifferentDepth_shouldWorkExactly(int depth) {
        BinDecisTree<Integer, Integer> tree = buildCompleteSubtree(
            new DynamicBinDecisTree<Integer, Integer>().builder(),
            alwaysTrue(),
            0,
            depth
        ).build();
        assertTrue(tree.isComplete());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void isCompleteOfUncompleteTreeWithDifferentDepth_shouldWorkExactly(int depth) {
        BinDecisTree<Integer, Integer> tree = buildCompleteSubtree(
            new DynamicBinDecisTree<Integer, Integer>().builder(),
            alwaysTrue(),
            0,
            depth
        ).insertCondition(alwaysTrue()).build();
        assertFalse(tree.isComplete());
    }
}
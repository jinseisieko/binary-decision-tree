package io.github.jinseisieko.decisiontree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.github.jinseisieko.decisiontree.TestUtilities.alwaysTrue;
import static io.github.jinseisieko.decisiontree.TestUtilities.assertThrowsWithNonEmptyMessage;
import static io.github.jinseisieko.decisiontree.TestUtilities.buildCompleteSubtree;

class DynamicDecisionTreeTest {
    @Test
    void decide_null_shouldThrowNullPointerException() {
        BinaryDecisionTree<Integer, Integer> tree = new DynamicDecisionTree<Integer, Integer>()
            .builder()
            .insertOutcome(0)
            .build();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> tree.decide(null));
    }

    @Test
    void decideOfUnbuiltTree_shouldThrowIllegalStateException() {
        BinaryDecisionTree<Integer, Integer> tree = new DynamicDecisionTree<>();
        assertThrowsWithNonEmptyMessage(IllegalStateException.class, () -> tree.decide(0));
    }

    @Test
    void isFullyDefinedOfUnbuiltTree_shouldThrowIllegalStateException() {
        BinaryDecisionTree<Integer, Integer> tree = new DynamicDecisionTree<>();
        assertThrowsWithNonEmptyMessage(IllegalStateException.class, () -> tree.isFullyDefined());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void isFullyDefinedOfCompleteTreeWithDifferentDepth_shouldWorkExactly(int depth) {
        BinaryDecisionTree<Integer, Integer> tree = buildCompleteSubtree(
            new DynamicDecisionTree<Integer, Integer>().builder(),
            alwaysTrue(),
            0,
            depth
        ).build();
        assertTrue(tree.isFullyDefined());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5})
    void isFullyDefinedOfIncompleteTreeWithDifferentDepth_shouldWorkExactly(int depth) {
        DecisionTreeBuilder<Integer, Integer> builder = buildCompleteSubtree(
            new DynamicDecisionTree<Integer, Integer>().builder(),
            alwaysTrue(),
            0,
            depth
        );
        builder.goToRoot();
        builder.goToFalseChild();
        builder.clearSubtree().insertOutcome(0);

        BinaryDecisionTree<Integer, Integer> tree = builder.build();
        assertFalse(tree.isFullyDefined());
    }

    @Test
    void getDepthOfUnbuiltTree_shouldThrowIllegalStateException() {
        BinaryDecisionTree<Integer, Integer> tree = new DynamicDecisionTree<>();
        assertThrowsWithNonEmptyMessage(IllegalStateException.class, () -> tree.getDepth());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void getDepth_shouldWorkExactly(int depth) {
        BinaryDecisionTree<Integer, Integer> tree = buildCompleteSubtree(
            new DynamicDecisionTree<Integer, Integer>().builder(),
            alwaysTrue(),
            0,
            depth
        ).build();
        assertEquals(depth, tree.getDepth());
    }
}

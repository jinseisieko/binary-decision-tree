package io.github.jinseisieko.bindecistree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.jinseisieko.bindecistree.HelperMethods.alwaysZero;
import static io.github.jinseisieko.bindecistree.HelperMethods.assertThrowsWithNonEmptyMessage;

class DynamicOutcomeNodeTest {

    @Test
    void dynamicOutcomeNode_shouldBeCompleteImmediatelyAfterCreation() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        Assertions.assertTrue(node.isComplete());
    }

    // --- Tests for DynamicOutcomeNode unsupported operations ---

    @Test
    void dynamicOutcomeNode_setTrueNode_shouldThrowUnsupportedOperationException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> otherNode = new DynamicOutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setTrueNode(otherNode));
    }

    @Test
    void dynamicOutcomeNode_setFalseNode_shouldThrowUnsupportedOperationException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> otherNode = new DynamicOutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setFalseNode(otherNode));
    }

    @Test
    void dynamicOutcomeNode_setAllNodes_shouldThrowUnsupportedOperationException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> trueNode = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> falseNode = new DynamicOutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setAllNodes(trueNode, falseNode));
    }
}
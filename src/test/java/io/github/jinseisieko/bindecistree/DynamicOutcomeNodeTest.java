package io.github.jinseisieko.bindecistree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.jinseisieko.bindecistree.TestUtilities.alwaysZero;
import static io.github.jinseisieko.bindecistree.TestUtilities.assertThrowsWithNonEmptyMessage;

class DynamicOutcomeNodeTest {

    @Test
    void isComplete_shouldBeTrueImmediatelyAfterCreation() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        Assertions.assertTrue(node.isComplete());
    }

    // --- Tests for DynamicOutcomeNode unsupported operations ---

    @Test
    void setTrueNode_shouldThrowUnsupportedOperationException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> otherNode = new DynamicOutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setTrueNode(otherNode));
    }

    @Test
    void setFalseNode_shouldThrowUnsupportedOperationException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> otherNode = new DynamicOutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setFalseNode(otherNode));
    }

    @Test
    void setAllNodes_shouldThrowUnsupportedOperationException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> trueNode = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> falseNode = new DynamicOutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setAllNodes(trueNode, falseNode));
    }
}
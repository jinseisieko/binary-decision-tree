package io.github.jinseisieko.bindecistree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.jinseisieko.bindecistree.HelperMethods.assertThrowsWithNonEmptyMessage;

class DynamicOutcomeNodeTest {

    @Test
    void dynamicOutcomeNode_shouldBeCompleteImmediatelyAfterCreation() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        Assertions.assertTrue(node.isComplete());
    }

    // --- Tests for DynamicOutcomeNode unsupported operations ---

    @Test
    void dynamicOutcomeNode_setTrueNode_shouldThrowUnsupportedOperationException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        DynamicBinDecisTreeNode<Integer, String> otherNode = new DynamicOutcomeNode<>();
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setTrueNode(otherNode));
    }

    @Test
    void dynamicOutcomeNode_setFalseNode_shouldThrowUnsupportedOperationException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        DynamicBinDecisTreeNode<Integer, String> otherNode = new DynamicOutcomeNode<>();
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setFalseNode(otherNode));
    }

    @Test
    void dynamicOutcomeNode_setAllNodes_shouldThrowUnsupportedOperationException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        DynamicBinDecisTreeNode<Integer, String> trueNode = new DynamicOutcomeNode<>();
        DynamicBinDecisTreeNode<Integer, String> falseNode = new DynamicOutcomeNode<>();
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setAllNodes(trueNode, falseNode));
    }
}
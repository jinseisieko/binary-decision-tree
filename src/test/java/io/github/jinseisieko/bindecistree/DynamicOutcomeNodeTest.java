package io.github.jinseisieko.bindecistree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DynamicOutcomeNodeTest {

    @Test
    void dynamicOutcomeNode_shouldBeCompleteImmediatelyAfterCreation() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        Assertions.assertTrue(node.isComplete());
    }

    @Test
    void dynamicOutcomeNode_cannotApplySetters() {
        Exception ex;
        DynamicBinDecisTreeNode<Integer, String> dynamicOutcomeNode = new DynamicOutcomeNode<>();
        DynamicBinDecisTreeNode<Integer, String> secondNode = new DynamicOutcomeNode<>();
        DynamicBinDecisTreeNode<Integer, String> thirdNode = new DynamicOutcomeNode<>();
        ex = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            dynamicOutcomeNode.setTrueNode(secondNode);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            dynamicOutcomeNode.setFalseNode(secondNode);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            dynamicOutcomeNode.setAllNodes(secondNode, thirdNode);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
    }
}
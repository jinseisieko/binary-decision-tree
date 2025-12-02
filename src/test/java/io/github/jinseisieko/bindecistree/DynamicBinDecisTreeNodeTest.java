package io.github.jinseisieko.bindecistree;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DynamicBinDecisTreeNodeTest {
    @Test
    void NodeSettersWithNull_shouldThrowExceptions() {
        Exception ex;
        DynamicBinDecisTreeNode<Integer, String> dynamicConditionNode = new DynamicConditionNode<>();
        DynamicBinDecisTreeNode<Integer, String> secondNode = new DynamicConditionNode<>();
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicConditionNode.setTrueNode(null);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicConditionNode.setFalseNode(null);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicConditionNode.setAllNodes(secondNode, null);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicConditionNode.setAllNodes(null, secondNode);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicConditionNode.setAllNodes(null, null);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());

        DynamicBinDecisTreeNode<Integer, String> dynamicOutcomeNode = new DynamicOutcomeNode<>();
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicOutcomeNode.setTrueNode(null);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicOutcomeNode.setFalseNode(null);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicOutcomeNode.setAllNodes(secondNode, null);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicOutcomeNode.setAllNodes(null, secondNode);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
        ex = Assertions.assertThrows(NullPointerException.class, () -> {
            dynamicOutcomeNode.setAllNodes(null, null);
        });
        Assertions.assertNotNull(ex);
        Assertions.assertFalse(ex.getMessage().isEmpty());
    }
}
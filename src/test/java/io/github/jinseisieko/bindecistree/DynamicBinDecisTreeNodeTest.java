package io.github.jinseisieko.bindecistree;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class DynamicBinDecisTreeNodeTest {
    
        // --- Tests for DynamicConditionNode ---

    @Test
    void dynamicConditionNode_setTrueNode_null_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicConditionNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setTrueNode(null));
    }

    @Test
    void dynamicConditionNode_setFalseNode_null_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicConditionNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setFalseNode(null));
    }

    @Test
    void dynamicConditionNode_setAllNodes_nullTrueNode_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicConditionNode<>();
        DynamicBinDecisTreeNode<Integer, String> falseNode = new DynamicConditionNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(null, falseNode));
    }

    @Test
    void dynamicConditionNode_setAllNodes_nullFalseNode_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicConditionNode<>();
        DynamicBinDecisTreeNode<Integer, String> trueNode = new DynamicConditionNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(trueNode, null));
    }

    @Test
    void dynamicConditionNode_setAllNodes_bothNull_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicConditionNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(null, null));
    }

    // --- Tests for DynamicOutcomeNode ---

    @Test
    void dynamicOutcomeNode_setTrueNode_null_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setTrueNode(null));
    }

    @Test
    void dynamicOutcomeNode_setFalseNode_null_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setFalseNode(null));
    }

    @Test
    void dynamicOutcomeNode_setAllNodes_nullTrueNode_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        DynamicBinDecisTreeNode<Integer, String> falseNode = new DynamicConditionNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(null, falseNode));
    }

    @Test
    void dynamicOutcomeNode_setAllNodes_nullFalseNode_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        DynamicBinDecisTreeNode<Integer, String> trueNode = new DynamicConditionNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(trueNode, null));
    }

    @Test
    void dynamicOutcomeNode_setAllNodes_bothNull_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicOutcomeNode<>();
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(null, null));
    }


    private void assertThrowsWithNonEmptyMessage(Class<? extends Throwable> expectedType, Executable executable) {
        Throwable exception = assertThrows(expectedType, executable);
        assertNotNull(exception);
        assertFalse(
            exception.getMessage() == null || exception.getMessage().isEmpty(),
            "Exception message should not be null or empty"
        );
    }
}
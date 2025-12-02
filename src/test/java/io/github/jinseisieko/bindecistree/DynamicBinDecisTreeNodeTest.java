package io.github.jinseisieko.bindecistree;


import org.junit.jupiter.api.Test;

import static io.github.jinseisieko.bindecistree.HelperMethods.alwaysTrue;
import static io.github.jinseisieko.bindecistree.HelperMethods.alwaysZero;
import static io.github.jinseisieko.bindecistree.HelperMethods.assertThrowsWithNonEmptyMessage;



class DynamicBinDecisTreeNodeTest {

    // --- Tests for DynamicConditionNode ---

    @Test
    void dynamicConditionNode_setTrueNode_null_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setTrueNode(null));
    }

    @Test
    void dynamicConditionNode_setFalseNode_null_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setFalseNode(null));
    }

    @Test
    void dynamicConditionNode_setAllNodes_nullTrueNode_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        DynamicBinDecisTreeNode<Integer, Integer> falseNode = new DynamicConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(null, falseNode));
    }

    @Test
    void dynamicConditionNode_setAllNodes_nullFalseNode_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        DynamicBinDecisTreeNode<Integer, Integer> trueNode = new DynamicConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(trueNode, null));
    }

    @Test
    void dynamicConditionNode_setAllNodes_bothNull_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(null, null));
    }

    // --- Tests for DynamicOutcomeNode ---

    @Test
    void dynamicOutcomeNode_setTrueNode_null_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setTrueNode(null));
    }

    @Test
    void dynamicOutcomeNode_setFalseNode_null_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setFalseNode(null));
    }

    @Test
    void dynamicOutcomeNode_setAllNodes_nullTrueNode_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> falseNode = new DynamicConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(null, falseNode));
    }

    @Test
    void dynamicOutcomeNode_setAllNodes_nullFalseNode_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        DynamicBinDecisTreeNode<Integer, Integer> trueNode = new DynamicConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(trueNode, null));
    }

    @Test
    void dynamicOutcomeNode_setAllNodes_bothNull_shouldThrowNullPointerException() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicOutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setAllNodes(null, null));
    }
}
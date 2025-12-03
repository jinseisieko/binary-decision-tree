package io.github.jinseisieko.bindecistree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static io.github.jinseisieko.bindecistree.TestUtilities.alwaysTrue;
import static io.github.jinseisieko.bindecistree.TestUtilities.assertThrowsWithNonEmptyMessage;

class DynamicConditionNodeTest {

    @Test
    void isComplete_shouldBeFalseImmediatelyAfterCreation() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        assertFalse(node.isComplete());
    }

    @Test
    void isComplete_shouldBeTrueWhenAllSet() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        DynamicBinDecisTreeNode<Integer, Integer> trueNode = new DynamicConditionNode<>(alwaysTrue());
        DynamicBinDecisTreeNode<Integer, Integer> falseNode = new DynamicConditionNode<>(alwaysTrue());
        node.setAllNodes(trueNode, falseNode);
        assertTrue(node.isComplete());
    }    

    @Test
    void isComplete_shouldBeFalseIfTrueNodeIsNull() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        node.setFalseNode(new DynamicConditionNode<>(alwaysTrue()));
        assertFalse(node.isComplete());
    }

    @Test
    void isComplete_shouldBeFalseIfFalseNodeIsNull() {
        DynamicBinDecisTreeNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        node.setTrueNode(new DynamicConditionNode<>(alwaysTrue()));
        assertFalse(node.isComplete());
    }

    @Test
    void setCondition_null_shouldThrowNullPointerException() {
        DynamicConditionNode<Integer, Integer> node = new DynamicConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setCondition(null));
    }
}
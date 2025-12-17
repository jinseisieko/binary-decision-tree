package io.github.jinseisieko.decisiontree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static io.github.jinseisieko.decisiontree.TestUtilities.alwaysTrue;
import static io.github.jinseisieko.decisiontree.TestUtilities.assertThrowsWithNonEmptyMessage;

class ConditionNodeTest {

    @Test
    void isComplete_shouldBeFalseImmediatelyAfterCreation() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        assertFalse(node.isComplete());
    }

    @Test
    void isComplete_shouldBeTrueWhenAllSet() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        AbstractDecisionNode<Integer, Integer> trueChild = new ConditionNode<>(alwaysTrue());
        AbstractDecisionNode<Integer, Integer> falseChild = new ConditionNode<>(alwaysTrue());
        node.setChildren(trueChild, falseChild);
        assertTrue(node.isComplete());
    }

    @Test
    void isComplete_shouldBeFalseIfTrueChildIsNull() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        node.setFalseChild(new ConditionNode<>(alwaysTrue()));
        assertFalse(node.isComplete());
    }

    @Test
    void isComplete_shouldBeFalseIfFalseChildIsNull() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        node.setTrueChild(new ConditionNode<>(alwaysTrue()));
        assertFalse(node.isComplete());
    }

    @Test
    void setCondition_null_shouldThrowNullPointerException() {
        ConditionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setCondition(null));
    }
}

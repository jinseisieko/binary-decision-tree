package io.github.jinseisieko.decisiontree;

import java.util.function.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.github.jinseisieko.decisiontree.TestUtilities.alwaysZero;
import static io.github.jinseisieko.decisiontree.TestUtilities.assertThrowsWithNonEmptyMessage;

class OutcomeNodeTest {

    @Test
    void outcomeNode_constructor_nullHandler_shouldThrowNullPointerException() {
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> new OutcomeNode<>((Function<Integer, Integer>) null));
    }

    @Test
    void outcomeNode_constructor_nullValue_shouldThrowNullPointerException() {
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> new OutcomeNode<>((Integer) null));
    }

    @Test
    void isComplete_shouldBeTrueImmediatelyAfterCreation() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        Assertions.assertTrue(node.isComplete());
    }

    // --- Tests for OutcomeNode unsupported operations ---

    @Test
    void setTrueChild_shouldThrowUnsupportedOperationException() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        AbstractDecisionNode<Integer, Integer> otherNode = new OutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setTrueChild(otherNode));
    }

    @Test
    void setFalseChild_shouldThrowUnsupportedOperationException() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        AbstractDecisionNode<Integer, Integer> otherNode = new OutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setFalseChild(otherNode));
    }

    @Test
    void setChildren_shouldThrowUnsupportedOperationException() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        AbstractDecisionNode<Integer, Integer> trueChild = new OutcomeNode<>(alwaysZero());
        AbstractDecisionNode<Integer, Integer> falseChild = new OutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(UnsupportedOperationException.class, () -> node.setChildren(trueChild, falseChild));
    }
}

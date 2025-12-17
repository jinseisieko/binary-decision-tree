package io.github.jinseisieko.decisiontree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import io.github.jinseisieko.decisiontree.TestUtilities.Counter;
import static io.github.jinseisieko.decisiontree.TestUtilities.alwaysTrue;
import static io.github.jinseisieko.decisiontree.TestUtilities.alwaysZero;
import static io.github.jinseisieko.decisiontree.TestUtilities.assertThrowsWithNonEmptyMessage;

class DecisionNodeTest {

    // --- Tests for ConditionNode ---

    @Test
    void conditionNode_setTrueChild_null_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setFalseChild(null));
    }

    @Test
    void conditionNode_setFalseChild_null_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setFalseChild(null));
    }

    @Test
    void conditionNode_setChildren_nullTrueChild_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        AbstractDecisionNode<Integer, Integer> falseChild = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setChildren(null, falseChild));
    }

    @Test
    void conditionNode_setChildren_nullFalseChild_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        AbstractDecisionNode<Integer, Integer> trueChild = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setChildren(trueChild, null));
    }

    @Test
    void conditionNode_setChildren_bothNull_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setChildren(null, null));
    }

    @Test
    void conditionNode_execute_null_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.execute(null));
    }

    // --- Tests for OutcomeNode ---

    @Test
    void outcomeNode_setTrueChild_null_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setTrueChild(null));
    }

    @Test
    void outcomeNode_setFalseChild_null_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setFalseChild(null));
    }

    @Test
    void outcomeNode_setChildren_nullTrueChild_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        AbstractDecisionNode<Integer, Integer> falseChild = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setChildren(null, falseChild));
    }

    @Test
    void outcomeNode_setChildren_nullFalseChild_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        AbstractDecisionNode<Integer, Integer> trueChild = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setChildren(trueChild, null));
    }

    @Test
    void outcomeNode_setChildren_bothNull_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.setChildren(null, null));
    }

    @Test
    void outcomeNode_execute_null_shouldThrowNullPointerException() {
        AbstractDecisionNode<Integer, Integer> node = new OutcomeNode<>(alwaysZero());
        assertThrowsWithNonEmptyMessage(NullPointerException.class, () -> node.execute(null));
    }

    // --- execute ---
    @Test
    void incompleteNode_execute_shouldCauseIllegalStateException() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        assertThrowsWithNonEmptyMessage(IllegalStateException.class, () -> node.execute(0));
    }

    @Test
    void incompleteStructure_execute_shouldCauseIllegalStateException() {
        AbstractDecisionNode<Integer, Integer> node = new ConditionNode<>(alwaysTrue());
        AbstractDecisionNode<Integer, Integer> trueChild = new ConditionNode<>(alwaysTrue());
        AbstractDecisionNode<Integer, Integer> falseChild = new ConditionNode<>(alwaysTrue());
        node.setTrueChild(trueChild);
        node.setFalseChild(falseChild);
        assertThrowsWithNonEmptyMessage(IllegalStateException.class, () -> node.execute(0));
    }

    // --- Use cases tests  ---

    @Test
    void shouldClassifyAge_correctly() {
        OutcomeNode<Integer, String> senior = new OutcomeNode<>("senior");
        OutcomeNode<Integer, String> adult = new OutcomeNode<>("adult");
        OutcomeNode<Integer, String> minor = new OutcomeNode<>("minor");

        ConditionNode<Integer, String> adultOrMinor = new ConditionNode<>(age -> age >= 18);
        adultOrMinor.setTrueChild(adult);
        adultOrMinor.setFalseChild(minor);

        ConditionNode<Integer, String> root = new ConditionNode<>(age -> age >= 65);
        root.setTrueChild(senior);
        root.setFalseChild(adultOrMinor);

        assertEquals("senior", root.execute(70));
        assertEquals("adult", root.execute(30));
        assertEquals("minor", root.execute(10));
        assertEquals("adult", root.execute(18));
        assertEquals("senior", root.execute(65));
    }

    @Test
    void conditionWithSideEffects_producesDifferentResultsOnSameTree() {
        OutcomeNode<Counter, String> evenOutcome = new OutcomeNode<>("even");
        OutcomeNode<Counter, String> oddOutcome = new OutcomeNode<>("odd");

        ConditionNode<Counter, String> root = new ConditionNode<>((Counter counter) -> {
            counter.increment();
            return counter.getValue() % 2 == 0;
        });

        root.setFalseChild(root.copy());
        root.getFalseChild().setFalseChild(oddOutcome.copy());
        root.getFalseChild().setTrueChild(evenOutcome.copy());

        root.setTrueChild(evenOutcome.copy());

        assertEquals("even", root.execute(new Counter(1)));
        assertEquals("even", root.execute(new Counter(0)));
        assertEquals("odd", root.getFalseChild().execute(new Counter(0)));
    }
}

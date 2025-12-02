package io.github.jinseisieko.bindecistree;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import io.github.jinseisieko.bindecistree.TestUtilities.Counter;
import static io.github.jinseisieko.bindecistree.TestUtilities.alwaysTrue;
import static io.github.jinseisieko.bindecistree.TestUtilities.alwaysZero;
import static io.github.jinseisieko.bindecistree.TestUtilities.assertThrowsWithNonEmptyMessage;



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

    // --- Use cases tests  ---

    @Test
    void shouldClassifyAge_correctly() {
        DynamicOutcomeNode<Integer, String> senior = new DynamicOutcomeNode<>("senior");
        DynamicOutcomeNode<Integer, String> adult = new DynamicOutcomeNode<>("adult");
        DynamicOutcomeNode<Integer, String> minor = new DynamicOutcomeNode<>("minor");

        DynamicConditionNode<Integer, String> adultOrMinor = new DynamicConditionNode<>(age -> age >= 18);
        adultOrMinor.setTrueNode(adult);
        adultOrMinor.setFalseNode(minor);

        DynamicConditionNode<Integer, String> root = new DynamicConditionNode<>(age -> age >= 65);
        root.setTrueNode(senior);
        root.setFalseNode(adultOrMinor);

        assertEquals("senior", root.execute(70));
        assertEquals("adult", root.execute(30));
        assertEquals("minor", root.execute(10));
        assertEquals("adult", root.execute(18));
        assertEquals("senior", root.execute(65)); 
    }

    @Test
    void conditionWithSideEffects_producesDifferentResultsOnSameTree() {
        DynamicOutcomeNode<Counter, String> evenOutcome = new DynamicOutcomeNode<>("even");
        DynamicOutcomeNode<Counter, String> oddOutcome = new DynamicOutcomeNode<>("odd");

        DynamicConditionNode<Counter, String> root = new DynamicConditionNode<>((Counter counter) -> {
            counter.increment();
            return counter.getValue() % 2 == 0;
        });

        root.setFalseNode(root.copy());
        root.getFalseNode().setFalseNode(oddOutcome.copy());
        root.getFalseNode().setTrueNode(evenOutcome.copy());

        root.setTrueNode(evenOutcome);
        
        assertEquals("even", root.execute(new Counter(1)));
        assertEquals("even", root.execute(new Counter(0)));
        assertEquals("odd", root.getTrueNode().execute(new Counter(0)));
    }
}
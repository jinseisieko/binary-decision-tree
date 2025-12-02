package io.github.jinseisieko.bindecistree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static io.github.jinseisieko.bindecistree.HelperMethods.alwaysTrue;

class DynamicConditionNodeTest {

    @Test
    void dynamicConditionNode_cannotBeCompleteImmediatelyAfterCreation() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicConditionNode<>(alwaysTrue());
        assertFalse(node.isComplete());
    }

    @Test
    void dynamicConditionNodeWithAllNodes_shouldBeComplete() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicConditionNode<>(alwaysTrue());
        DynamicBinDecisTreeNode<Integer, String> trueNode = new DynamicConditionNode<>(alwaysTrue());
        DynamicBinDecisTreeNode<Integer, String> falseNode = new DynamicConditionNode<>(alwaysTrue());
        node.setAllNodes(trueNode, falseNode);
        assertTrue(node.isComplete());
    }    
}
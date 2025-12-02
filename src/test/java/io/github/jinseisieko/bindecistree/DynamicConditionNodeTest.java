package io.github.jinseisieko.bindecistree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DynamicConditionNodeTest {

    @Test
    void dynamicConditionNode_cannotBeCompleteImmediatelyAfterCreation() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicConditionNode<>();
        assertFalse(node.isComplete());
    }

    @Test
    void dynamicConditionNodeWithAllNodes_shouldBeComplete() {
        DynamicBinDecisTreeNode<Integer, String> node = new DynamicConditionNode<>();
        DynamicBinDecisTreeNode<Integer, String> trueNode = new DynamicConditionNode<>();
        DynamicBinDecisTreeNode<Integer, String> falseNode = new DynamicConditionNode<>();
        node.setAllNodes(trueNode, falseNode);
        assertTrue(node.isComplete());
    }    
}
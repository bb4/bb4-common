package com.barrybecker4.common.search;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class NodeTest {


    @Test
    public void testInitialNodeConstruction() {
        StubState state = new StubState(1);
        Node<StubState, StubTransition> node = new Node<>(state, 5);

        assertEquals("Unexpected future cost", 5, node.getEstimatedFutureCost());
        assertEquals("Unexpected state", state, node.getState());
        assertEquals("Unexpected path length", 0, node.asTransitionList().size());
    }

    @Test
    public void testTwoNodePathConstruction() {
        StubState state = new StubState(1);
        Node<StubState, StubTransition> initialNode = new Node<>(state, 5);

        StubState state2 = new StubState(2);
        StubTransition transition = new StubTransition(1);
        Node<StubState, StubTransition> node = new Node<>(state2, transition, initialNode, 4);

        assertEquals("Unexpected future cost", 4, node.getEstimatedFutureCost());
        assertEquals("Unexpected path length", 1, node.asTransitionList().size());
    }

    @Test
    public void testThreeNodePathConstruction() {
        StubState state = new StubState(1);
        Node<StubState, StubTransition> initialNode = new Node<>(state, 5);

        StubState state2 = new StubState(2);
        StubTransition transition = new StubTransition(1);
        Node<StubState, StubTransition> node1 = new Node<>(state2, transition, initialNode, 4);

        StubState state3 = new StubState(3);
        StubTransition transition2 = new StubTransition(2);
        Node<StubState, StubTransition> node2 = new Node<>(state2, transition, node1, 2);

        assertEquals("Unexpected path length", 2, node2.asTransitionList().size());
    }
}

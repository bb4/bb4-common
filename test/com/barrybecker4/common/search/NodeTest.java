package com.barrybecker4.common.search;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        StubTransition transition = new StubTransition(state2);
        Node<StubState, StubTransition> node = new Node<>(state2, transition, initialNode, 4);

        assertEquals("Unexpected future cost", 4, node.getEstimatedFutureCost());
        assertEquals("Unexpected path length", 1, node.asTransitionList().size());
    }

    @Test
    public void testThreeNodePathConstruction() {
        StubState state = new StubState(1);
        Node<StubState, StubTransition> initialNode = new Node<>(state, 5);

        StubState state2 = new StubState(2);
        StubTransition transition = new StubTransition(state2);
        Node<StubState, StubTransition> node1 = new Node<>(state2, transition, initialNode, 4);

        StubState state3 = new StubState(3);
        StubTransition transition2 = new StubTransition(state3);
        Node<StubState, StubTransition> node2 = new Node<>(state3, transition2, node1, 2);

        assertEquals("Unexpected path length", 2, node2.asTransitionList().size());
    }

    @Test
    public void testSort() {

        StubState state = new StubState(1);
        StubTransition transition = new StubTransition(state);
        Node<StubState, StubTransition> initialNode = new Node<>(state, 5);
        Node<StubState, StubTransition> node1 = new Node<>(state, transition, initialNode, 7);
        Node<StubState, StubTransition> node2 = new Node<>(state, transition, initialNode, 1);
        Node<StubState, StubTransition> node3 = new Node<>(state, transition, initialNode, 4);

        List<Node<StubState, StubTransition>> nodes = Arrays.asList(initialNode, node1, node2, node3);

        Collections.sort(nodes);
        assertEquals("Unexpected ordering.",
                "[[id=1 distanceFromGoal=0, cost=1], " +
                "[id=1 distanceFromGoal=0, cost=4], " +
                "[id=1 distanceFromGoal=0, cost=5], " +
                "[id=1 distanceFromGoal=0, cost=7]]",
                nodes.toString());
    }
}
package com.barrybecker4.common.search;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class HeapPriorityQueueTest extends UpdatablePriorityQueueTest {

    public UpdatablePriorityQueue<StubState, StubTransition> createQueue() {
        return new HeapPriorityQueue<>();
    }

    /** make sure nodes popped off in sorted order */
    @Test
    public void testPopFromFour() {
        StubState state1 = new StubState("3", 3);
        StubState state2 = new StubState("5", 5);
        StubState state3 = new StubState("1", 1);
        StubState state4 = new StubState("4", 4);
        pq.add(new Node<StubState, StubTransition>(state1, null, null, 16, 17));
        pq.add(new Node<StubState, StubTransition>(state2, null, null, 10, 20));
        pq.add(new Node<StubState, StubTransition>(state3, null, null, 6, 12));
        pq.add(new Node<StubState, StubTransition>(state4, null, null, 9, 18));

        Node<StubState, StubTransition> node = pq.pop();
        assertEquals("Unexpected not smallest", 6, node.getPathCost());
        assertEquals("Unexpected size after adding one node", state3, node.getState());

        node = pq.pop();
        assertEquals("Unexpected not second smallest", 16, node.getPathCost());
        assertEquals("Unexpected not second smallest", 17, node.getEstimatedFutureCost());
        assertEquals("Unexpected size after adding one node", state1, node.getState());

        node = pq.pop();
        assertEquals("Unexpected not third smallest", 9, node.getPathCost());
        assertEquals("Unexpected size after adding one node", state4, node.getState());

        assertEquals("Unexpected size after adding one node", 1, pq.size());
    }

}

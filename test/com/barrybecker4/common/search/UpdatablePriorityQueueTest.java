package com.barrybecker4.common.search;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;


/**
 * @author Barry Becker
 */
public abstract class UpdatablePriorityQueueTest {

    protected UpdatablePriorityQueue<StubState, StubTransition> pq;


    protected abstract UpdatablePriorityQueue<StubState, StubTransition> createQueue();

    @Before
    public void setUp() {
        pq = createQueue();
    }

    @Test
    public void testInitiallyEmpty() {
        assertTrue("Unexpected not empty", pq.isEmpty());
    }


    @Test
    public void testHasOneAfterAdd() {
        StubState state = new StubState("3", 3);
        pq.add(new Node<StubState, StubTransition>(state));
        assertFalse("Unexpectedly empty", pq.isEmpty());
        assertEquals("Unexpected size after adding one node", 1, pq.size());
    }

    @Test
    public void testPopFromOne() {
        StubState state = new StubState("3", 3);
        pq.add(new Node<StubState, StubTransition>(state, null, null, 6, 12));
        Node<StubState, StubTransition> node = pq.pop();

        assertTrue("Unexpectedly not empty", pq.isEmpty());
        assertEquals("Unexpected pathcost", 6, node.getPathCost());
        assertEquals("Unexpected size after adding one node", state, node.getState());
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


    /** make sure addOrUpdate acts as update if node not there. */
    @Test
    public void testAddOrUpdateFour() {
        StubState state1 = new StubState("3", 3);
        StubState state2 = new StubState("5", 5);
        StubState state3 = new StubState("1", 1);
        StubState state4 = new StubState("4", 4);
        pq.addOrUpdate(new Node<StubState, StubTransition>(state1, null, null, 16, 17));
        pq.addOrUpdate(new Node<StubState, StubTransition>(state2, null, null, 10, 20));
        pq.addOrUpdate(new Node<StubState, StubTransition>(state3, null, null, 6, 14));
        pq.addOrUpdate(new Node<StubState, StubTransition>(state4, null, null, 9, 11));

        Node<StubState, StubTransition> node = pq.pop();
        assertEquals("Unexpected not smallest", 9, node.getPathCost());
        assertEquals("Unexpected size after adding one node", state4, node.getState());

        node = pq.pop();
        assertEquals("Unexpected not second smallest", 6, node.getPathCost());
        assertEquals("Unexpected not second smallest", 14, node.getEstimatedFutureCost());
        assertEquals("Unexpected size after adding one node", state3, node.getState());

        node = pq.pop();
        assertEquals("Unexpected not third smallest", 16, node.getPathCost());
        assertEquals("Unexpected size after adding one node", state1, node.getState());

        assertEquals("Unexpected size after adding one node", 1, pq.size());
    }


    /** make that we can update the priority of a node in the heap */
    @Test
    public void testUpdateInFour() {
        StubState state1 = new StubState("3", 3);
        StubState state2 = new StubState("5", 5);
        StubState state3 = new StubState("1", 1);
        StubState state4 = new StubState("4", 4);
        pq.addOrUpdate(new Node<StubState, StubTransition>(state1, null, null, 16, 17));
        pq.addOrUpdate(new Node<StubState, StubTransition>(state2, null, null, 10, 20));
        pq.addOrUpdate(new Node<StubState, StubTransition>(state3, null, null, 6, 14));
        pq.addOrUpdate(new Node<StubState, StubTransition>(state4, null, null, 9, 11));

        pq.addOrUpdate(new Node<StubState, StubTransition>(state2, null, null, 20, 12));
        Node<StubState, StubTransition> node = pq.pop();
        assertEquals("Unexpected not smallest", 9, node.getPathCost());
        assertEquals("Unexpected size after adding one node", state4, node.getState());

        node = pq.pop();
        assertEquals("Unexpected not second smallest", 20, node.getPathCost());
        assertEquals("Unexpected not second smallest", 12, node.getEstimatedFutureCost());
        assertEquals("Unexpected size after adding one node", state2, node.getState());

        assertEquals("Unexpected size after adding one node", 2, pq.size());
    }

}

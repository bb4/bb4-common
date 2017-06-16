package com.barrybecker4.common.searchold;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class AStarSearchTest {

    private StubSearchSpace space;

    @Before
    public void setUp() {
        space = new StubSearchSpace();
    }


    /** test the protected search method to see if it finds the goal state */
    @Test
    public void testSolve() {
        AStarSearch<StubState, StubTransition> searcher = new AStarSearch<>(space);

        // a list of transitions to the goal state
        List<StubTransition> path = searcher.solve();
        assertEquals("Unexpected path to goal state found.",
                "[[id=D distanceFromGoal=5 cost=4], " +
                "[id=E distanceFromGoal=4 cost=6], " +
                "[id=goal distanceFromGoal=0 cost=4]]",
                path.toString());
    }
}

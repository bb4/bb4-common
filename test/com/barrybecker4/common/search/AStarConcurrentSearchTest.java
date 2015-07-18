package com.barrybecker4.common.search;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class AStarConcurrentSearchTest {

    private StubSearchSpace space;

    @Before
    public void setUp() {
        space = new StubSearchSpace();
    }



    /** test the protected search method to see if it finds the goal state */
    @Test
    public void testSolve() {
        AStarSearch<StubState, StubTransition> searcher = new AStarConcurrentSearch<>(space);

        // a list of transitions to the goal state
        List<StubTransition> path = searcher.solve();
        /*
        assertEquals("Unexpected path to goal state found.",
                "[[id=A distanceFromGoal=8 cost=3], " +
                "[id=B distanceFromGoal=7 cost=4], " +
                "[id=C distanceFromGoal=6 cost=6], " +
                "[id=goal distanceFromGoal=0 cost=8]]",
                path.toString()); */

        // this is what it should be.
        assertEquals("Unexpected path to goal state found.",
                "[[id=D distanceFromGoal=5 cost=4], " +
                "[id=E distanceFromGoal=4 cost=6], " +
                "[id=goal distanceFromGoal=0 cost=4]]",
                path.toString());

        // [[id=[D distanceFromGoal=5 cost=4],
        // [id=E distanceFromGoal=4 cost=6],
        // [id=goal distanceFromGoal=0 cost=4]]]>
        // but was:<
        // [[id=[A distanceFromGoal=8 cost=3],
        // [id=B distanceFromGoal=7 cost=4],
        // [id=C distanceFromGoal=6 cost=6],
        // [id=goal distanceFromGoal=0 cost=8]]]>

        //[[id=A distanceFromGoal=8 cost=3],
        //[id=B distanceFromGoal=7 cost=4], [id=C distanceFromGoal=6 cost=6], [id=goal distanceFromGoal=0 cost=8]]
    }
}

package com.barrybecker4.common.search;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Barry Becker
 */
public class DualAStarSearchTest {

    private StubSearchSpace space;
    private ReverseStubSearchSpace reverseSpace;

    @Before
    public void setUp() {
        space = new StubSearchSpace();
        reverseSpace = new ReverseStubSearchSpace();
    }


    /** test the protected search method to see if it finds the goal state */
    @Test
    public void testSolve() {
        AStarSearch<StubState, StubTransition> fwdSearch = new AStarSearch<>(space);
        AStarSearch<StubState, StubTransition> bwdSearch = new AStarSearch<>(reverseSpace);
        DualAStarSearch<StubState, StubTransition> searcher = new DualAStarSearch<>(fwdSearch, bwdSearch);

        // a list of transitions to the goal state
        List<StubTransition> path = searcher.solve();
        assertEquals("Unexpected path to goal state found.",
                "[[id=D distanceFromGoal=5 cost=4], " +
                "[id=E distanceFromGoal=4 cost=6], " +
                "[id=goal distanceFromGoal=0 cost=4]]",
                path.toString());
    }

    /** test searching the other way */
    @Test
    public void testSolveReverse() {
        AStarSearch<StubState, StubTransition> fwdSearch = new AStarSearch<>(space);
        AStarSearch<StubState, StubTransition> bwdSearch = new AStarSearch<>(reverseSpace);
        DualAStarSearch<StubState, StubTransition> searcher = new DualAStarSearch<>(bwdSearch, fwdSearch);

        // a list of transitions to the goal state
        List<StubTransition> path = searcher.solve();
        assertEquals("Unexpected path to goal state found.",
                "[[id=D distanceFromGoal=5 cost=4], " +
                        "[id=E distanceFromGoal=4 cost=6], " +
                        "[id=goal distanceFromGoal=0 cost=4]]",
                path.toString());
    }
}

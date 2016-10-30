package com.barrybecker4.common.search.slidingpuzzletests;

import com.barrybecker4.common.Watch;
import com.barrybecker4.common.search.HeapPriorityQueue;
import com.barrybecker4.common.search.slidingpuzzle.Board;
import com.barrybecker4.common.search.slidingpuzzle.Solver;
import com.barrybecker4.common.search.slidingpuzzle.Transition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author Barry Becker
 */
public class HeapQSolverTest extends SolverTest {

    public Solver createSolver(Board initial) {
        return new Solver(initial, new HeapPriorityQueue<Board, Transition>());
    }


    @Test
    public void testSolveMedium() {
        int testNum = 11;
        String file = "puzzle" + testNum + ".txt";
        Board initial = reader.read(file);

        Watch timer = new Watch();
        solver = createSolver(initial);
        double elapsed = timer.getElapsedTime();

        System.out.println("elapsed = " + elapsed + " seconds.");
        assertEquals("Unexpected number of moves for " + file,
                testNum, solver.moves());
        assertEquals(file + " unexpectedly not solvable", true, solver.isSolvable());
        assertTrue("Took too long " + elapsed, elapsed < 10.0);
    }
}
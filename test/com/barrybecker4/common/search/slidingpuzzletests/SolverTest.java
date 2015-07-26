package com.barrybecker4.common.search.slidingpuzzletests;

import com.barrybecker4.common.Watch;
import com.barrybecker4.common.search.slidingpuzzle.Board;
import com.barrybecker4.common.search.slidingpuzzle.BoardReader;
import com.barrybecker4.common.search.slidingpuzzle.Solver;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * @author Barry Becker
 */
public abstract class SolverTest {

    protected Solver solver;
    protected BoardReader reader;

    @Before
    public void setUp() {
         reader = new BoardReader();
    }

    protected abstract Solver createSolver(Board initial);

    @Test
    public void testSolve8() {
        Board initial = reader.read("puzzle08.txt");
        solver = createSolver(initial);

        assertEquals("Unexpected number of moves for puzzle8.txt",
                8, solver.moves());
        assertEquals("Unexpectedly not solvable", true, solver.isSolvable());
    }

    @Test
    public void testSolve10by10inGoalState() {

        String file = "puzzle00.txt";
        Board initial = reader.read(file);
        solver = createSolver(initial);

        assertEquals("Unexpected number of moves for " + file,
                0, solver.moves());
        assertEquals(file + " unexpectedly not solvable", true, solver.isSolvable());
    }

    @Test
    public void testSolve07() {
        Board initial = reader.read("puzzle07.txt");
        solver = createSolver(initial);

        assertEquals("Unexpected number of moves for puzzle07.txt", 7, solver.moves());
        String path = getSolutionSequence(solver.solution());
        assertTrue("Unexpected solution", path.equals(
                "3\n" +
                        " 1  2  3 \n" +
                        " 0  7  6 \n" +
                        " 5  4  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 7  0  6 \n" +
                        " 5  4  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 7  4  6 \n" +
                        " 5  0  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 7  4  6 \n" +
                        " 0  5  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 0  4  6 \n" +
                        " 7  5  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 4  0  6 \n" +
                        " 7  5  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 4  5  6 \n" +
                        " 7  0  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 4  5  6 \n" +
                        " 7  8  0 \n") || path.equals(
                "3\n" +
                        " 1  2  3 \n" +
                        " 0  7  6 \n" +
                        " 5  4  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 5  7  6 \n" +
                        " 0  4  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 5  7  6 \n" +
                        " 4  0  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 5  0  6 \n" +
                        " 4  7  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 0  5  6 \n" +
                        " 4  7  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 4  5  6 \n" +
                        " 0  7  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 4  5  6 \n" +
                        " 7  0  8 \n" +
                        "3\n" +
                        " 1  2  3 \n" +
                        " 4  5  6 \n" +
                        " 7  8  0 \n"));
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

    private String getSolutionSequence(Iterable<Board> seq) {
        StringBuilder bldr  = new StringBuilder();
        for (Board b : seq) {
            bldr.append(b.toString());
        }
        return bldr.toString();
    }

    /*
    @Test
    public void testNotSolvable() {
        Board initial = reader.read("puzzle3x3-unsolvable.txt");
        solver = createSolver(initial);
        assertEquals("Unexpected number of moves for puzzle3x3-unsolvable.txt", -1, solver.moves());
        assertEquals("Unexpectedly solvable", false, solver.isSolvable());
    }  */

    @Test
    public void runAllSolvableTestFiles() {
        List<Case> testCases = new LinkedList<>();
        testCases.add(new Case("puzzle00.txt", 0, true));

        for (int i = 1; i < 49; i++) {
            String filename = "puzzle";
            if (i < 10) {
               filename += "0";
            }
            testCases.add(new Case(filename + i + ".txt", i, true));
        }

        runCases(testCases, 30.0);
    }


    @Test
    public void runAllUnsolvableTestFiles() {
        List<Case> testCases = new LinkedList<>();
        testCases.add(new Case("puzzle2x2-unsolvable1.txt", -1, false));
        testCases.add(new Case("puzzle2x2-unsolvable2.txt", -1, false));
        testCases.add(new Case("puzzle2x2-unsolvable3.txt", -1, false));
        testCases.add(new Case("puzzle3x3-unsolvable.txt", -1, false));
        testCases.add(new Case("puzzle3x3-unsolvable1.txt", -1, false));
        testCases.add(new Case("puzzle3x3-unsolvable2.txt", -1, false));

        runCases(testCases, 2.0);
    }

    @Test
    public void run2by2Cases() {
        List<Case> testCases = new LinkedList<>();
        testCases.add(new Case("puzzle2x2-solvable1.txt", 4, true));
        testCases.add(new Case("puzzle2x2-solvable2.txt", 4, true));

        runCases(testCases, 0.5);
    }

    /*
    @Test
    public void testSolveSpecific47() {
        doRun(47, 30.0);
    }*/

    /*
    @Test
    public void testSolveSpecific() {
        doRun(48, 30.0);
    }

    @Test
    public void testSolveLarge4x4() {
        doRun(34, 4.0);
    }  */

    private void doRun(int testNum, double timeLimit) {
        String file = "puzzle" + testNum + ".txt";
        Board initial = reader.read(file);

        Watch timer = new Watch();
        solver = createSolver(initial);
        double elapsed = timer.getElapsedTime();

        System.out.println("elapsed = " + elapsed + " seconds.");
        assertEquals("Unexpected number of moves for " + file,
                testNum, solver.moves());
        assertEquals(file + " unexpectedly not solvable", true, solver.isSolvable());
        assertTrue("Took too long " + elapsed, elapsed < timeLimit);
    }

    protected void runCases(List<Case> testCases, double timeLimitSecs) {
        Watch timer = new Watch();
        for (Case testCase : testCases) {
            runCase(testCase);
        }
        double elapsed = timer.getElapsedTime();
        System.out.println("Elapsed time = " + elapsed + " seconds.");
        assertTrue("Took too long: " + elapsed + "seconds. Wanted " + timeLimitSecs,
                elapsed < timeLimitSecs);
        assertTrue("TOO FAST!?!: " + elapsed + "seconds.", elapsed > (timeLimitSecs/500.0));
    }

    private void runCase(Case testCase) {
        System.out.println(testCase.filename);
        Board initial = reader.read(testCase.filename);
        solver = createSolver(initial);

        assertEquals("Unexpected number of moves for " + testCase.filename,
                testCase.expNumMoves, solver.moves());
        if (testCase.expIsSolvable) {
            assertTrue("Unexpectedly not solvable", solver.isSolvable());
            Iterable<Board> sol = solver.solution();
            for (Board b : sol) {
                System.out.println(b);
            }
        }
        else {
            assertFalse("Unexpectedly solvable", solver.isSolvable());
            assertNull("Solution not null", solver.solution());
        }
    }
}

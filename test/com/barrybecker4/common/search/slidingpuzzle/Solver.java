package com.barrybecker4.common.search.slidingpuzzle;

import com.barrybecker4.common.search.AStarSearch;
import com.barrybecker4.common.search.SearchSpace;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Barry Becker
 */
public class Solver {

    private Board startState;
    private List<Transition> solution;

    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial starting board state
     */
    public Solver(final Board initial)   {
        startState = initial;
        final SearchSpace<Board, Transition> space = new PuzzleSearchSpace(initial);
        final SearchSpace<Board, Transition> twinSpace = new PuzzleSearchSpace(initial.twin());

        final AStarSearch<Board, Transition> searcher = new AStarSearch<>(space);
        final AStarSearch<Board, Transition> twinSearcher = new AStarSearch<>(twinSpace);

        new Thread(new Runnable() {
            @Override
            public void run() {
                searcher.solve();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                twinSearcher.solve();
            }
        }).start();

        while (searcher.getSolution() == null && twinSearcher.getSolution() == null) {
            // keep searching

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (searcher.getSolution() != null) {
            solution = searcher.getSolution();
            twinSearcher.stop();
        }
        else {
            solution = null; // no solution
            searcher.stop();
        }
    }

    /** @return true if the initial board is solvable */
    public boolean isSolvable() {
        return solution != null;
    }

    /** @return min number of moves to solve initial board; -1 if unsolvable */
    public int moves() {
        return solution == null ? -1 : solution.size();
    }

    /** @return sequence of boards in a shortest solution; null if unsolvable */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        List<Board> list = new LinkedList<>();
        list.add(startState);
        for (Transition trans : solution) {
            list.add(startState.applyTransition(trans));
        }
        return list; //solution.asTransitionList();
    }


    /** solve a slider puzzle */
    public static void main(String[] args)  {

        Board initial = new BoardReader().read(args[0]);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                System.out.println(board);
        }
    }
}
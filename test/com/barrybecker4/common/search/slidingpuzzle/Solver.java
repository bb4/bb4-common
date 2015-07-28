package com.barrybecker4.common.search.slidingpuzzle;

import com.barrybecker4.common.search.AStarSearch;
import com.barrybecker4.common.search.HeapPriorityQueue;
import com.barrybecker4.common.search.SearchSpace;
import com.barrybecker4.common.search.UpdatablePriorityQueue;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Barry Becker
 */
public class Solver {

    private Board startState;
    private List<Transition> solutionTransitions;

    /**
     * find a solution to the initial board (using the A* algorithm)
     * @param initial starting board state
     */
    public Solver(final Board initial, UpdatablePriorityQueue<Board, Transition> queue)   {
        startState = initial;
        //solveWithoutAssumptions(initial, queue);
        solveAssumingSolvable(initial, queue);
    }

    /** this is faster and simpler if we know its solvable */
    private void solveAssumingSolvable(Board initial, UpdatablePriorityQueue<Board, Transition> queue) {
        final SearchSpace<Board, Transition> space = new PuzzleSearchSpace(initial);

        final AStarSearch<Board, Transition> searcher = new AStarSearch<>(space, queue);
        searcher.solve();
        solutionTransitions = searcher.getSolution();
    }

    /*
    private void solveWithoutAssumptions(Board initial, UpdatablePriorityQueue queue) {
        final SearchSpace<Board, Transition> space = new PuzzleSearchSpace(initial);
        final SearchSpace<Board, Transition> twinSpace = new PuzzleSearchSpace(initial.twin());

        final AStarSearch<Board, Transition> searcher = new AStarSearch<>(space, queue);
        final AStarSearch<Board, Transition> twinSearcher = new AStarSearch<>(twinSpace, queue);

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
            //try {
            //    Thread.sleep(100);
            //} catch (InterruptedException e) {
            //    e.printStackTrace();
            //}
        }

        if (searcher.getSolution() != null) {
            solutionTransitions = searcher.getSolution();
            twinSearcher.stop();
        }
        else {
            solutionTransitions = null; // no solution
            searcher.stop();
        }
    }*/

    /** @return true if the initial board is solvable */
    public boolean isSolvable() {
        return solutionTransitions != null;
    }

    /** @return min number of moves to solve initial board; -1 if unsolvable */
    public int moves() {
        return solutionTransitions == null ? -1 : solutionTransitions.size();
    }

    /** @return sequence of boards in a shortest solutionTransitions; null if unsolvable */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        List<Board> list = new LinkedList<>();
        list.add(startState);
        Board previous = startState;

        for (Transition trans : solutionTransitions) {
            Board newState = previous.applyTransition(trans);
            list.add(newState);
            previous = newState;
        }
        return list; //solutionTransitions.asTransitionList();
    }


    /** solve a slider puzzle */
    public static void main(String[] args)  {

        Board initial = new BoardReader().read(args[0]);

        // solve the puzzle
        Solver solver = new Solver(initial, new HeapPriorityQueue<Board, Transition>());

        // print solutionTransitions to standard output
        if (!solver.isSolvable())
            System.out.println("No solutionTransitions possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                System.out.println(board);
        }
    }
}
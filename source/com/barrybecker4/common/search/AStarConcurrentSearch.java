/** Copyright by Barry G. Becker, 2012-2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;

import com.barrybecker4.common.concurrency.RunnableParallelizer;
import com.barrybecker4.common.concurrency.ThreadUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Concurrent implementation of the A* search algorithm.
 * Finds the optimal path to a goal state (S) from an initial state (S) via a sequence of transitions (T).
 * See http://en.wikipedia.org/wiki/A*_search_algorithm
 * See http://math.owu.edu/MCURCSM/papers/paper2.pdf
 * S - State
 * T - Transition from one state to the next.
 * @author Barry Becker
 */
public class AStarConcurrentSearch<S, T> extends AStarSearch<S, T> {

    /** use the number of cores available as a default number of threads */
    private static final int NUM_WORKERS = RunnableParallelizer.NUM_PROCESSORS;

    /**
     * @param searchSpace the global search space that contains initial and goal states.
     */
    public AStarConcurrentSearch(SearchSpace<S, T> searchSpace) {
        this.searchSpace = searchSpace;
        visited = Collections.synchronizedMap(new HashMap<S, Node<S, T>>());
        //openQueue = new PriorityBlockingQueue<>(20);
        openQueue = new HeapPriorityQueue<>();  // this probably will not work since it is not thread safe
        pathCost = Collections.synchronizedMap(new HashMap<S, Integer>());
    }

    /**
     * Best first search for a solution.
     * @return the solution state node if found which has the path leading to a solution. Null if no solution.
     */
    protected Node<S, T> doSearch() {

        RunnableParallelizer parallelizer = new RunnableParallelizer();

        List<Runnable> workers = new ArrayList<>(NUM_WORKERS);
        for (int i = 0; i < NUM_WORKERS; i++) {
            workers.add(new Worker(this));
        }
        // blocks until all Callables are done running.
        parallelizer.invokeAllRunnables(workers);

        return solution;
    }

    /**
     * Since this version is concurrent, initially we might ask for nodes off the queue
     * faster than they are added. That is the reason for the short sleep.
     * @return true if nodes are in the queue and not found a solution yet.
     */
    protected synchronized boolean nodesAvailable() {
        if (openQueue.isEmpty()) {
            ThreadUtil.sleep(10);
        }
        return !openQueue.isEmpty() && solution == null;
    }

    /** search worker */
    class Worker implements Runnable {
        AStarSearch<S, T> solver;

        Worker(AStarSearch<S, T> solver) {
            this.solver = solver;
        }

        @Override
        public void run() {
            Node<S, T> sol = solver.search();
            if (sol != null)  {
                solution = sol;
            }
        }
    }
}

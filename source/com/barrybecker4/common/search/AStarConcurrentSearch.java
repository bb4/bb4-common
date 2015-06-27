/** Copyright by Barry G. Becker, 2012-2014. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;

import com.barrybecker4.common.concurrency.Parallelizer;
import com.barrybecker4.common.concurrency.ThreadUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Concurrent implementation of the A* search algorithm.
 * Finds the optimal path to a goal state from an initial state.
 * See http://en.wikipedia.org/wiki/A*_search_algorithm
 * @author Barry Becker
 */
public class AStarConcurrentSearch<P, M> extends AStarSearch<P, M> {

    /** use the number of cores available as a default number of threads */
    private static final int NUM_WORKERS = Parallelizer.NUM_PROCESSORS;


    /**
     * @param searchSpace the global search space that contains initial and goal states.
     */
    public AStarConcurrentSearch(SearchSpace<P, M> searchSpace) {
        this.searchSpace = searchSpace;
        visited = Collections.synchronizedSet(new HashSet<P>());
        openQueue = new PriorityBlockingQueue<>(20);
        pathCost = Collections.synchronizedMap(new HashMap<P, Integer>());
    }

    /**
     * Best first search for a solution.
     * @return the solution state node if found which has the path leading to a solution. Null if no solution.
     */
    protected Node<P, M> doSearch() {

        Parallelizer parallelizer = new Parallelizer<>();

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
         AStarSearch<P, M> solver;

        Worker(AStarSearch<P, M> solver) {
            this.solver = solver;
        }

        @Override
        public void run() {
            Node<P, M> sol = solver.search();
            if (sol != null)  {
                solution = sol;
            }
        }
    }
}

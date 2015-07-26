/** Copyright by Barry G. Becker, 2012 - 2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;

import java.util.*;

/**
 * Sequential search strategy that uses the A* search algorithm.
 * See http://en.wikipedia.org/wiki/A*_search_algorithm
 * S represents a state in the global search space.
 * T represents a transition from one state to the next.
 *
 * The performance of this search is very dependent on the design of the search space.
 * Here are some possible optimizations to consider when designing the SearchSpace and its components.
 * - The visited list may grow huge if the space is very large causing out of memory issues.
 * - Calculate distance metrics in the constructor (or using lazy initialization) of S. S and T should be immutable.
 * - Try to make the equals method in S as efficient as possible as it will be called a lot.
 * - When creating neighbors, use the fact that there is going to be an incremental change to the distance
 *   and do not recompute it from scratch. Hint: use a private constructor, that takes the distance as a param.
 * - Sort the neighbors so that the most promising is delivered first.
 *
 * @author Barry Becker
 */
public class AStarSearch<S, T>  {

    protected SearchSpace<S, T> searchSpace;

    /** States that have been visited, but they may be replaced if we can reach them by a better path */
    protected Set<S> visited;

    /** Candidate nodes to search on the frontier. */
    protected UpdatablePriorityQueue<S, T> openQueue;

    /** Provides the value for the lowest cost path from the start state to the specified goal state (g score) */
    protected Map<S, Integer> pathCost;

    protected volatile Node<S, T> solution;

    /** number of steps that it took to find solution */
    protected long numTries;

    /** enables stopping the search via method call */
    private boolean stopped;

    /**
     * @param searchSpace the global search space containing initial and goal states.
     */
    public AStarSearch(SearchSpace<S, T> searchSpace) {
        this(searchSpace, new HeapPriorityQueue<S, T>());
    }

    /**
     * @param searchSpace the global search space containing initial and goal states.
     * @param queue the specific updatable priority queue to use.
     */
    public AStarSearch(SearchSpace<S, T> searchSpace, UpdatablePriorityQueue<S, T> queue) {
        this.searchSpace = searchSpace;
        visited = new HashSet<>();
        openQueue = queue;
        pathCost = new HashMap<>();
    }

    protected AStarSearch() {}

    /**
     * @return a sequence of transitions leading from the initial state to the goal state.
     */
    public List<T> solve() {

        stopped = false;
        S startingState = searchSpace.initialState();
        long startTime = System.currentTimeMillis();
        Node<S, T> startNode =
                new Node<>(startingState, searchSpace.distanceFromGoal(startingState));
        openQueue.add(startNode);
        pathCost.put(startingState, 0);

        Node<S, T> solutionState = doSearch();

        List<T> pathToSolution = null;
        S solution = null;
        if (solutionState != null) {
            pathToSolution = solutionState.asTransitionList();
            solution = solutionState.getState();
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        searchSpace.finalRefresh(pathToSolution, solution, numTries, elapsedTime);
        return pathToSolution;
    }

    /** @return the solution - null until it is found */
    public List<T> getSolution() {
        return solution == null ? null : solution.asTransitionList();
    }

    /** Tell the search to stop */
    public void stop() {
        stopped = true;
    }

    /**
     * Best first search for a solution.
     * @return the solution state node if found which has the path leading to a solution. Null if no solution.
     */
    protected Node<S, T> doSearch() {
        return search();
    }

    /**
     * Best first search for a solution.
     * @return the solution state node, if found, which has the path leading to a solution. Null if no solution.
     */
    protected Node<S, T> search() {

        while (nodesAvailable() && !stopped)  {
            Node<S, T> currentNode = openQueue.pop();
            //System.out.println("popped from ["+openQueue.size()+"] = " + currentNode);
            S currentState = currentNode.getState();
            searchSpace.refresh(currentState, numTries);

            if (searchSpace.isGoal(currentState)) {
                // the extra check for a better path is needed when running concurrently
                if (solution == null || currentNode.getPathCost() < solution.getPathCost()) {
                     solution = currentNode;
                }
                return currentNode;  // success
            }
            visited.add(currentState);
            List<T> transitions = searchSpace.legalTransitions(currentState);
            for (T transition : transitions) {
                S nbr = searchSpace.transition(currentState, transition);
                if (!visited.contains(nbr)) {
                    //if (pathCost.get(currentState) == null) {
                    //    System.out.println("pathCost " + currentState+"= " + pathCost.get(currentState));
                    //    System.out.println("map = " + pathCost);
                    //}

                    int estPathCost = pathCost.get(currentState) + searchSpace.getCost(transition);
                    if (!pathCost.containsKey(nbr) || estPathCost < pathCost.get(nbr)) {
                        int estFutureCost = estPathCost + searchSpace.distanceFromGoal(nbr);
                        Node<S, T> child =
                                new Node<>(nbr, transition, currentNode, estPathCost, estFutureCost);
                        pathCost.put(nbr, estPathCost);
                        openQueue.addOrUpdate(child);
                        numTries++;
                    }
                }
            }
        }
        return null;  // failure
    }

    protected boolean nodesAvailable() {
        return !openQueue.isEmpty();
    }
}





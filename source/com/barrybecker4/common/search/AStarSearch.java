/** Copyright by Barry G. Becker, 2012 - 2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Sequential search strategy that uses the A* search algorithm.
 * See http://en.wikipedia.org/wiki/A*_search_algorithm
 * S represents a state in the global search space.
 * T represents a transition from one state to the next.
 *
 * @author Barry Becker
 */
public class AStarSearch<S, T>  {

    protected SearchSpace<S, T> searchSpace;

    /** States that have been visited, but they may be replaced if we can reach them by a better path */
    protected Set<S> visited;

    /** Candidate nodes to search on the frontier. */
    protected Queue<Node<S, T>> openQueue;

    /** Provides the value for the lowest cost path from the start state to the specified goal state (g score) */
    protected Map<S, Integer> pathCost;

    protected volatile Node<S, T> solution;

    /** number of steps that it took to find solution */
    protected long numTries;

    /**
     * @param searchSpace the global search space containing initial and goal states.
     */
    public AStarSearch(SearchSpace<S, T> searchSpace) {
        this.searchSpace = searchSpace;
        visited = new HashSet<>();
        openQueue = new PriorityQueue<>(20);
        pathCost = new HashMap<>();
    }

    protected AStarSearch() {}

    /**
     * @return a sequence of transitions leading from the initial state to the goal state.
     */
    public List<T> solve() {

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

        while (nodesAvailable())  {
            Node<S, T> currentNode = openQueue.remove();
            S currentState = currentNode.getState();
            searchSpace.refresh(currentState, numTries);

            if (searchSpace.isGoal(currentState)) {
                solution = currentNode;
                return currentNode;  // success
            }
            visited.add(currentState);
            List<T> transitions = searchSpace.legalTransitions(currentState);
            for (T transition : transitions) {
                S nbr = searchSpace.transition(currentState, transition);
                int estPathCost = pathCost.get(currentState) + searchSpace.getCost(transition);
                if (!openQueue.contains(nbr) || estPathCost < pathCost.get(nbr)) {
                    int estFutureCost = estPathCost + searchSpace.distanceFromGoal(nbr);
                    Node<S, T> child =
                            new Node<>(nbr, transition, currentNode, estFutureCost);
                    pathCost.put(nbr, estPathCost);
                    if (!openQueue.contains(child)) {
                        openQueue.add(child);
                        numTries++;
                    }
                }
            }
        }
        return null;  // failure. No solution found.
    }

    protected boolean nodesAvailable() {
        return !openQueue.isEmpty();
    }
}

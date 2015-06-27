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
 * A concurrent version of this algorithm could be made using a PriorityBlockingQueue for {@code openQueue}
 * P represents a Position (or state) of the problem being searched.
 *   A sequence of positions lead from initial position to goal position.
 * M represnets a move, or a transition from one position to the next
 * TODO: P->S  M->T (transition)
 *
 * @author Barry Becker
 */
public class AStarSearch<P, M>  {

    protected SearchSpace<P, M> searchSpace;

    /** nodes that have been visited, but they may be replaced if we can reach them by a better path */
    protected Set<P> visited;

    /** candidate nodes to search on the frontier. */
    protected Queue<Node<P, M>> openQueue;

    /** provides the value for the lowest cost path from the start node to the specified node (g score) */
    protected Map<P, Integer> pathCost;

    protected volatile Node<P, M> solution;

    /** number of steps to find solution */
    protected long numTries;

    /**
     * @param searchSpace the global search space containing initial and goal states.
     */
    public AStarSearch(SearchSpace<P, M> searchSpace) {
        this.searchSpace = searchSpace;
        visited = new HashSet<>();
        openQueue = new PriorityQueue<>(20);
        pathCost = new HashMap<>();
    }

    protected AStarSearch() {}

    public List<M> solve() {

        P startingPos = searchSpace.initialPosition();
        long startTime = System.currentTimeMillis();
        Node<P, M> startNode =
                new Node<>(startingPos, searchSpace.distanceFromGoal(startingPos));
        openQueue.add(startNode);
        pathCost.put(startingPos, 0);

        Node<P, M> solutionState = doSearch();

        List<M> pathToSolution = null;
        P solution = null;
        if (solutionState != null) {
            pathToSolution = solutionState.asMoveList();
            solution = solutionState.getPosition();
        }
        long elapsedTime = System.currentTimeMillis() - startTime;
        searchSpace.finalRefresh(pathToSolution, solution, numTries, elapsedTime);
        return pathToSolution;
    }

    /**
     * Best first search for a solution.
     * @return the solution state node if found which has the path leading to a solution. Null if no solution.
     */
    protected Node<P, M> doSearch() {
        return search();
    }

    /**
     * Best first search for a solution.
     * @return the solution state node if found which has the path leading to a solution. Null if no solution.
     */
    protected Node<P, M> search() {

        while (nodesAvailable())  {
            Node<P, M> currentNode = openQueue.remove();
            P currentPosition = currentNode.getPosition();
            searchSpace.refresh(currentPosition, numTries);

            if (searchSpace.isGoal(currentPosition)) {
                solution = currentNode;
                return currentNode;  // success
            }
            visited.add(currentPosition);
            List<M> moves = searchSpace.legalMoves(currentPosition);
            for (M move : moves) {
                P nbr = searchSpace.move(currentPosition, move);
                int estPathCost = pathCost.get(currentPosition) + searchSpace.getCost(move);
                if (!visited.contains(nbr) || estPathCost < pathCost.get(nbr)) {
                    int estFutureCost = estPathCost + searchSpace.distanceFromGoal(nbr);
                    Node<P, M> child =
                            new Node<>(nbr, move, currentNode, estFutureCost);
                    pathCost.put(nbr, estPathCost);
                    if (!openQueue.contains(child)) {
                        openQueue.add(child);
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

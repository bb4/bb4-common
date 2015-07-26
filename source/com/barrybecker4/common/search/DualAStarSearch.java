/** Copyright by Barry G. Becker, 2012 - 2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;

import java.util.*;

/**
 * Run two AStar searches simultaneously.
 * One searches from the start to the goal state, while
 * the other searches from the goal to the start state.
 * If ever one reaches a state in the visited list of the other, we are done.
 *
 * @author Barry Becker
 */
public class DualAStarSearch<S, T> implements ISearcher {

    private AStarSearch<S, T> startToGoalSearch;
    private AStarSearch<S, T> goalToStartSearch;

    protected volatile Node<S, T> solution;


    /**
     * @param startToGoalSearch search from start to goal
     * @param goalToStartSearch search from goal to start
     */
    public DualAStarSearch(AStarSearch<S, T> startToGoalSearch, AStarSearch<S, T> goalToStartSearch) {
        this.startToGoalSearch = startToGoalSearch;
        this.goalToStartSearch = goalToStartSearch;
    }

    /**
     * @return a sequence of transitions leading from the initial state to the goal state.
     */
    public List<T> solve() {

        startToGoalSearch.initialize();
        goalToStartSearch.initialize();
        Node<S, T> solutionState = search();

        List<T> pathToSolution = null;
        if (solutionState != null) {
            pathToSolution = solutionState.asTransitionList();
        }
        return pathToSolution;
    }

    /** @return the solution - null until it is found */
    public List<T> getSolution() {
        return solution == null ? null : solution.asTransitionList();
    }

    /** Tell the search to stop */
    public void stop() {
        startToGoalSearch.stop();
        goalToStartSearch.stop();
    }

    /**
     * Best first search for a solution.
     * @return the solution state node, if found, which has the path leading to a solution. Null if no solution.
     */
    protected Node<S, T> search() {

        while (startToGoalSearch.continueSearching()) { // && goalToStartSearch.continueSearching())  {

            Node<S, T> solutionNode = processNext(startToGoalSearch, goalToStartSearch);
            if (solutionNode == null) {
                solutionNode = processNext(goalToStartSearch, startToGoalSearch);
                // ? need to reverse in this case
            }
            if (solutionNode != null) {
                solution = solutionNode;
                return solutionNode;
            }
        }
        return null;  // failure
    }

    private Node<S, T> processNext(AStarSearch<S, T> startToGoalSearch, AStarSearch<S, T> goalToStartSearch) {
        Node<S, T> currentNode = startToGoalSearch.openQueue.pop();
        if (goalToStartSearch.visited.containsKey(currentNode.getState())) {
            // add the nodes from the other search
            Node<S, T> node = goalToStartSearch.visited.get(currentNode.getState());
            Node<S, T> nextNode = node.getPrevious();
            node.setPrevious(currentNode.getPrevious());
            // make the path to the finish include thos nodes in the other search
            while (nextNode != null) {
                Node<S, T> temp = nextNode.getPrevious();
                nextNode.setPrevious(node);
                node = nextNode;
                nextNode = temp;
            }
            return node;
        }
        Node<S, T> solutionNode = startToGoalSearch.processNext(currentNode);
        if (solutionNode != null) {
            solution = solutionNode;
            return solutionNode;
        }
        return null;
    }
}





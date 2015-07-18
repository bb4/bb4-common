package com.barrybecker4.common.search.slidingpuzzle;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Node for a state in the global search space.
 * A search node is a board, the number of moves made to reach the board, and the previous search node.
 *
 * @author Barry Becker
 */
//@Immutable
public class Node implements Comparable<Node> {

    private final Board state;
    private final int numSteps;
    private Node previous;

    public Node(Board state, Node prev)  {
        this(state, prev, 0);
    }

    /**
     * Use this only when there is no transition to this node.
     * @param initialState initial state
     * @param pathCost cost from initial position to the state represented by this node.
     */
    public Node(Board initialState, int pathCost) {
        this(initialState, null, pathCost);
    }

    /**
     * Represents a state and how we got to it from the last state.
     * @param state the current state state
     * @param prev the previous state
     * @param pathCost cost from initial position to the state represented by this node.
     */
    public Node(Board state, Node prev, int pathCost) {
        this.state = state;
        this.previous = prev;
        this.numSteps = pathCost;
    }

    /** @return state in the global search space */
    public Board getState() {
        return state;
    }

    public Board getPreviousBoard() {
        return previous == null ? null : previous.getState();
    }

    /** @return number of steps to get from the initial state to the current one */
    public int getNumSteps() {
        return numSteps;
    }

    public int getPriority() {
        return getNumSteps() + state.manhattan(); // * MULTIPLIER + state.hamming();
    }

    /**
     * @return a list of nodes from the start state to this state.
     *
    public List<Board> asTransitionList() {
        List<Board> solution = new LinkedList<>();
        solution.add(this.getState());
        for (Node n = this; n.previous != null; n = n.previous) {
            solution.add(n.previous.getState());
        }
        Collections.reverse(solution);
        return solution;
    }   */

    public String toString() {
        return "[" + state + ", numSteps="+ numSteps + "]";
    }

    @Override
    public int compareTo(Node n) {
        return this.getPriority() - n.getPriority();
    }
}

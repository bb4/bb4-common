/** Copyright by Barry G. Becker, 2012 - 2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;


import java.util.LinkedList;
import java.util.List;

/**
 * Link node for a state in the global search space.
 * Contains an immutable state and a transition that got us to this state from the last one.
 * The estimated future cost is used for sorting nodes.
 * S is the state type
 * T is the transition from one state to the next.
 *
 * @author Barry Becker
 */
//@Immutable
public class Node<S, T> implements Comparable<Node<S, T>> {

    private final S state;
    private final T transition;
    private final int pathCost;
    /** an estimate of the total eventual cost of the path from start to goal running through this node */
    private final int estimatedFutureCost;
    private Node<S, T> previous;

    public Node(S state, T transition, Node<S, T> prev) {
        this(state, transition, prev, 0, 1);
    }

    public Node(S state) {
        this(state, null, null, 0,  1);
    }

    /**
     * Use this only when there is no transition to this node.
     * @param initialState initial state
     * @param estimatedFutureCost the cost of getting here plus the estimated future cost to get to the finish.
     */
    public Node(S initialState, int estimatedFutureCost) {
        this(initialState, null, null, 0, estimatedFutureCost);
    }

    /**
     * Represents a state and how we got to it from the last state.
     * @param state the current state state
     * @param transition the transformation that got to this state
     * @param prev the previous state
     * @param pathCost cost from initial position to the state represented by this node
     * @param estimatedFutureCost the cost of getting here plus the estimated future cost to get to the finish.
     */
    public Node(S state, T transition, Node<S, T> prev, int pathCost, int estimatedFutureCost) {
        this.state = state;
        this.transition = transition;
        this.previous = prev;
        this.pathCost = pathCost;
        this.estimatedFutureCost = estimatedFutureCost;
    }

    /** @return state in the global search space */
    public S getState() {
        return state;
    }

    public int getPathCost() {
        return pathCost;
    }
    /**
     * @return An estimate of how much it will cost to go from this state to the goal state
     */
    public int getEstimatedFutureCost() {
        return estimatedFutureCost;
    }

    public Node<S, T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<S, T> p) {
        previous = p;
    }

    @Override
    public int compareTo(Node<S, T> otherNode) {
        return getEstimatedFutureCost() - otherNode.getEstimatedFutureCost();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?, ?> node = (Node<?, ?>) o;
        return state.equals(node.state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    /**
     * @return a list of nodes from the start state to this state.
     */
    public List<T> asTransitionList() {
        List<T> solution = new LinkedList<>();
        for (Node<S, T> node = this; node.transition != null; node = node.previous) {
            solution.add(0, node.transition);
        }
        return solution;
    }

    public String toString() {
        return "[" + state + ", pathCost="+ pathCost + " totalCost="+  estimatedFutureCost +"]";
    }
}

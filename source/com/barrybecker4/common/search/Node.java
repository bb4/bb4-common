/** Copyright by Barry G. Becker, 2012 - 2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;


import java.util.LinkedList;
import java.util.List;

/**
 * Link node for a position/state in the global search space.
 * Contains a position (immutable state) and a move
 * (transition that got us to that position from the previous state).
 * The estimated future cost is used for sorting nodes.
 * P is the position/state type
 * M is the move/transition from one position to another.
 *
 * @author Barry Becker
 */
//@Immutable
public class Node<P, M> implements Comparable<Node<P, M>> {

    private final P position;
    private final M move;
    private final int estimatedFutureCost;
    private Node<P, M> previous;

    public Node(P pos, M move, Node<P, M> prev) {
        this(pos, move, prev, 1);
    }

    public Node(P pos) {
        this(pos, null, null, 1);
    }

    public Node(P pos, int estimatedFutureCost) {
        this(pos, null, null, estimatedFutureCost);
    }

    /**
     * Puzzle state
     * @param pos the current position state
     * @param move the transformation that got to this state
     * @param prev the previous state
     * @param estimatedFutureCost the cost of getting here plus the estimated future cost to get to the finish.
     */
    public Node(P pos, M move, Node<P, M> prev, int estimatedFutureCost) {
        this.position = pos;
        this.move = move;
        this.previous = prev;
        this.estimatedFutureCost = estimatedFutureCost;
    }

    /** @return position in the global search space*/
    public P getPosition() {
        return position;
    }

    /**
     * @return An estimate of how much it will cost to go from this position to the goal state
     */
    public int getEstimatedFutureCost() {
        return estimatedFutureCost;
    }

    /**
     * @return a list of nodes from the start state to this state.
     */
    public List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for (Node<P, M> n = this; n.move != null; n = n.previous) {
            solution.add(0, n.move);
        }
        return solution;
    }

    @Override
    public int compareTo(Node<P, M> otherNode) {
        return getEstimatedFutureCost() - otherNode.getEstimatedFutureCost();
    }
}

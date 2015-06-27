/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;

import java.util.List;
import java.util.Set;

/**
 * Represents the global search space of all position states.
 * It must include an initial state and a goal state.
 * The type parameters P and M correspond to a position (state) and a move (transition from one state to the next).
 *
 * @author Barry Becker
 */
public interface SearchSpace<P, M> extends Refreshable<P, M>  {

    P initialPosition();

    /**
     * @return true if the position is the goal state.
     */
    boolean isGoal(P position);

    /**
     * @return a list of legal next immutable moves.
     */
    List<M> legalMoves(P position);

    /**
     * @return the position (immutable) that you get after applying the specified move.
     */
    P move(P position, M move);

    /**
     * Add the position to the seen set of position if not already seen.
     *
     * @param position to check
     * @param seen Map of seen positions.
     * @return true if the specified position was already seen (possibly taking into account symmetries).
     */
    boolean alreadySeen(P position, Set<P> seen);

    /**
     * @return estimate of the cost to reach the goal from the specified position.
     */
    int distanceFromGoal(P position);

    /**
     * @return the cost of making a single move. Usually a constant like 1, but for some scenarios it matters.
     */
    int getCost(M move);

}

/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;

import java.util.List;
import java.util.Set;

/**
 * Represents the global search space of all states.
 * It must include an initial state and a goal state.
 * The type parameters S and T correspond to a state and a transition from one state to the next.
 *
 * @author Barry Becker
 */
public interface SearchSpace<S, T> extends Refreshable<S, T>  {

    S initialState();

    /**
     * @return true if the state is the goal state.
     */
    boolean isGoal(S state);

    /**
     * @return a list of legal next immutable transitions.
     */
    List<T> legalTransitions(S state);

    /**
     * @return the state (immutable) that you get after applying the specified transition.
     */
    S transition(S state, T transition);

    /**
     * Add the state to the seen set of state if not already seen.
     *
     * @param state to check
     * @param seen Map of seen states.
     * @return true if the specified state was already seen (possibly taking into account symmetries).
     */
    boolean alreadySeen(S state, Set<S> seen);

    /**
     * @return estimate of the cost to reach the goal from the specified state.
     */
    int distanceFromGoal(S state);

    /**
     * @return the cost of making a single transition.
     * Usually a constant like 1, but for some scenarios it matters.
     */
    int getCost(T transition);

}

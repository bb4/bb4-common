/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.searchold;

import java.util.List;

/**
 * A UI element that can be refreshed to show the current state.
 *
 * @author Barry Becker
 */
public interface Refreshable<S, T> {

    /**
     * Call when you want the UI to update.
     * @param state if the current state to show.
     * @param numTries number of tries so far.
     */
    void refresh(S state, long numTries);

    /**
     *Show the path to the goal state at the end.
     *@param path list of transitions that gets to the solution. If path is null then no solution was found.
     *@param state the final state in the path. It may be null if no solution was found.
     *@param numTries number of tries it took to find that final state.
     *@param elapsedMillis number of milliseconds it took to find the solution.
     */
    void finalRefresh(List<T> path, S state, long numTries, long elapsedMillis);
}

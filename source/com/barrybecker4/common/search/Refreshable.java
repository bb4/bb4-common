/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.search;

import java.util.List;

/**
 * A UI element that can be refreshed to show the current state.
 *
 * @author Barry Becker
 */
public interface Refreshable<P, M> {

    /**
     * Call when you want the UI to update.
     * @param pos if the current position to show.
     * @param numTries number of tries so far.
     */
    void refresh(P pos, long numTries);

    /**
     *Show the path to the solution at the end.
     *@param path list of moves that gets to the solution. If path is null then no solution was found.
     *@param position the final board state in the path. It may be null if no solution was found.
     *@param numTries number of tries it took to find that final state.
     *@param elapsedMillis number of milliseconds it took to find the solution.
     */
    void finalRefresh(List<M> path, P position, long numTries, long elapsedMillis);
}

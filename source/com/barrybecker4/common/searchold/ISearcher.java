/** Copyright by Barry G. Becker, 2012 - 2015. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.searchold;

import java.util.List;

/**
 * Run two AStar searches simultaneously.
 * One searches from the start to the goal state, while
 * the other searches from the goal to the start state.
 * If ever one reaches a state in the visited list of the other, we are done.
 *
 * @author Barry Becker
 */
public interface ISearcher<S, T>  {

    /**
     * @return a sequence of transitions leading from the initial state to the goal state.
     */
    List<T> solve();

    /** @return the solution - null until it is found */
    List<T> getSolution();

    /** Tell the search to stop */
    void stop();

}





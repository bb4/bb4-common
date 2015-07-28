package com.barrybecker4.common.search;

import java.util.List;
import java.util.Set;

/**
 * Describes the search space.
 * @author Barry Becker
 */
public abstract class AbstractSearchSpace<S, T> implements SearchSpace<S, T> {

    protected S initialState;

    public AbstractSearchSpace(S initialState) {
        this.initialState = initialState;
    }

    @Override
    public S initialState() {
        return initialState;
    }

    @Override
    public boolean alreadySeen(S state, Set<S> seen) {
        if (!seen.contains(state)) {
            seen.add(state);
            return false;
        }
        return true;
    }

    @Override
    public int getCost(T transition) {
        return 1;
    }

    @Override
    public void refresh(S state, long numTries) {
    }

    @Override
    public void finalRefresh(List<T> path, S state, long numTries, long elapsedMillis) {
         // nothing be default
    }
}

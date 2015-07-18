package com.barrybecker4.common.search.slidingpuzzle;

import com.barrybecker4.common.search.SearchSpace;

import java.util.List;
import java.util.Set;

/**
 * @author Barry Becker
 */
public class PuzzleSearchSpace implements SearchSpace<Board, Transition> {

    private Board initialState;

    PuzzleSearchSpace(Board initialState) {
        this.initialState = initialState;
    }

    @Override
    public Board initialState() {
        return initialState;
    }

    @Override
    public boolean isGoal(Board state) {
        return state.hamming() == 0;
    }

    @Override
    public List<Transition> legalTransitions(Board state) {
        return state.getNeighborTransitions();
    }

    @Override
    public Board transition(Board state, Transition transition) {
        return state.applyTransition(transition);
    }

    @Override
    public boolean alreadySeen(Board state, Set<Board> seen) {
        if (!seen.contains(state)) {
            seen.add(state);
            return false;
        }
        return true;
    }

    @Override
    public int distanceFromGoal(Board state) {
        return state.manhattan();
    }

    @Override
    public int getCost(Transition transition) {
        return 1;
    }

    @Override
    public void refresh(Board state, long numTries) {

    }

    @Override
    public void finalRefresh(List<Transition> path, Board state, long numTries, long elapsedMillis) {
         // nothing
    }
}

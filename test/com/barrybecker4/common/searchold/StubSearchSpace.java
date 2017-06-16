package com.barrybecker4.common.searchold;

import java.util.*;

/**
 * A fake, very simple, search space for use in unit tests.
 * Teh StubNodes define a network, with costs on edges, that can be searched by A*.
 * See https://en.wikipedia.org/wiki/A*_search_algorithm (where I get this example)
 *
 * @author Barry Becker
 */
public class StubSearchSpace extends AbstractSearchSpace<StubState, StubTransition> {

    private static final StubProblem PROBLEM = new StubProblem();

    /** Constructor */
    StubSearchSpace() {
        super(PROBLEM.getInitialState());
    }

    @Override
    public boolean isGoal(StubState state) {
        return state.equals(PROBLEM.getGoalState());
    }

    @Override
    public List<StubTransition> legalTransitions(StubState state) {
        return PROBLEM.getLegalTransitions(state);
    }

    @Override
    public StubState transition(StubState state, StubTransition transition) {
        return transition.getNewState();
    }


    @Override
    public int distanceFromGoal(StubState state) {
        return state.getDistanceFromGoal();
    }

    @Override
    public int getCost(StubTransition transition) {
        return transition.getCost();
    }
}

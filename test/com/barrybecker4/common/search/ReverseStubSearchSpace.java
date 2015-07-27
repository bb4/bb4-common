package com.barrybecker4.common.search;

import java.util.List;

/**
 * This space looks from goal back to initial state.
 *
 * @author Barry Becker
 */
public class ReverseStubSearchSpace extends AbstractSearchSpace<StubState, StubTransition> {

    private static final StubProblem PROBLEM = new StubProblem();

    /** Constructor */
    ReverseStubSearchSpace() {
        super(PROBLEM.getGoalState());
    }

    @Override
    public boolean isGoal(StubState state) {
        return state.equals(PROBLEM.getInitialState());
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

package com.barrybecker4.common.search;

import java.util.*;

/**
 * A fake, very simple, search space for use in unit tests.
 * Teh StubNodes define a network, with costs on edges, that can be searched by A*.
 * See https://en.wikipedia.org/wiki/A*_search_algorithm (where I get this example)
 *
 *    "start"
 *   / (3)     \ (4)
 *   A            D
 *   | (4)        | (6)
 *   B            E
 *   | (6)        | (4)
 *   C ---(8)---  "goal"
 *
 * @author Barry Becker
 */
public class StubSearchSpace implements SearchSpace<StubState, StubTransition> {

    StubState initialState;
    StubState goalState;
    Map<StubState, List<StubTransition>> transitionMap = new HashMap<>();

    /** Cosntructor */
    StubSearchSpace() {
        initialState = new StubState("start", 9);
        StubState stateA = new StubState("A", 8);
        StubState stateB = new StubState("B", 7);
        StubState stateC = new StubState("C", 6);
        StubState stateD = new StubState("D", 5);
        StubState stateE = new StubState("E", 4);
        goalState = new StubState("goal", 0);

        transitionMap.put(initialState, Arrays.asList(new StubTransition(stateA, 3), new StubTransition(stateD, 4)));
        transitionMap.put(stateA, Collections.singletonList(new StubTransition(stateB, 4)));
        transitionMap.put(stateB, Collections.singletonList(new StubTransition(stateC, 6)));
        transitionMap.put(stateC, Collections.singletonList(new StubTransition(goalState, 8)));
        transitionMap.put(stateD, Collections.singletonList(new StubTransition(stateE, 6)));
        transitionMap.put(stateE, Collections.singletonList(new StubTransition(goalState, 4)));
    }

    @Override
    public StubState initialState() {
        return initialState;
    }

    @Override
    public boolean isGoal(StubState state) {
        return state.equals(goalState);
    }

    @Override
    public List<StubTransition> legalTransitions(StubState state) {
        return transitionMap.get(state);
    }

    @Override
    public StubState transition(StubState state, StubTransition transition) {
        return transition.getNewState();
    }

    @Override
    public boolean alreadySeen(StubState state, Set<StubState> seen) {
        if (!seen.contains(state)) {
            seen.add(state);
            return false;
        }
        return true;
    }

    @Override
    public int distanceFromGoal(StubState state) {
        return state.getDistanceFromGoal();
    }

    @Override
    public int getCost(StubTransition transition) {
        return transition.getCost();
    }

    @Override
    public void refresh(StubState state, long numTries) {
         // nothing
    }

    @Override
    public void finalRefresh(List<StubTransition> path, StubState state, long numTries, long elapsedMillis) {
         // nothing
    }
}

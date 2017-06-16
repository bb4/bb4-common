package com.barrybecker4.common.searchold;

import java.util.*;

/**
 * A fake, very simple, example search problem for use in unit tests.
 * The StubNodes define a network, with costs on edges, that can be searched by A*.
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
public class StubProblem {

    private StubState initialState;
    private StubState goalState;
    private Map<StubState, List<StubTransition>> transitionMap = new HashMap<>();

    /** Constructor */
    StubProblem() {
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
        transitionMap.put(stateC, Arrays.asList(new StubTransition(goalState, 8), new StubTransition(stateB, 8)));
        transitionMap.put(stateD, Collections.singletonList(new StubTransition(stateE, 6)));
        transitionMap.put(stateE, Arrays.asList(new StubTransition(goalState, 4), new StubTransition(stateD, 6)));
        transitionMap.put(goalState, Arrays.asList(new StubTransition(stateE, 4), new StubTransition(stateC, 8)));
    }

    public StubState getInitialState() {
        return initialState;
    }

    public StubState getGoalState() {
        return goalState;
    }

    public List<StubTransition> getLegalTransitions(StubState state) {
        return transitionMap.get(state);
    }
}

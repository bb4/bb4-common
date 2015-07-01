package com.barrybecker4.common.search;

/**
 * @author Barry Becker
 */
public class StubTransition {

    StubState newState;
    int cost;

    /**
     * @param newState the new state to be transitioned to.
     */
    StubTransition(StubState newState) {
        this.newState = newState;
    }

    /**
     * @param newState the new state to be transitioned to.
     * @param cost the cost of making this transition
     */
    StubTransition(StubState newState, int cost) {
        this(newState);
        this.cost = cost;
    }

    StubState getNewState() {
        return newState;
    }

    int getCost() {
        return cost;
    }


    public String toString() {
        return "[" + newState + " cost=" + cost  + "]";
    }
}

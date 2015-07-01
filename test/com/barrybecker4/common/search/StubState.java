package com.barrybecker4.common.search;

/**
 * @author Barry Becker
 */
public class StubState {

    private String id;
    int estDistanceFromGoal;

    /**
     * @param id some unique identifier
     */
    StubState(String id) {
         this.id = id;
    }

    /**
     * @param id some unique identifier
     */
    StubState(int id) {
         this.id = Integer.toString(id);
    }

    /**
     * @param id some unique identifier
     * @param estDistanceFromGoal the estimated distance to goal should be an optimistic estimate in order to
     *            be admissible by A*.
     */
    StubState(String id, int estDistanceFromGoal) {
        this(id);
        this.estDistanceFromGoal = estDistanceFromGoal;
    }


    int getDistanceFromGoal() {
        return estDistanceFromGoal;
    }

    public String toString() {
         return "id=" + id + " distanceFromGoal=" + estDistanceFromGoal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StubState stubState = (StubState) o;

        return id.equals(stubState.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}

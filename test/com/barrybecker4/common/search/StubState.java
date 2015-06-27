package com.barrybecker4.common.search;

/**
 * @author Barry Becker
 */
public class StubState {

    private int id;

    /**
     * @param id some unique identifier
     */
    StubState(int id) {
         this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StubState stubState = (StubState) o;

        return id == stubState.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}

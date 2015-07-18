package com.barrybecker4.common.search.slidingpuzzle;

import com.barrybecker4.common.geometry.Location;

/**
 * Move from one board state to another by shifting 1 tile to the space position
 * @author Barry Becker
 */
public class Transition {

    private Location spacePosition;
    private Location tilePosition;

    public Transition(Location spacePosition, Location tilePosition) {
        this.spacePosition = spacePosition;
        this.tilePosition = tilePosition;
    }

    public Location getSpacePosition() {
        return spacePosition;
    }

    public Location getTilePosition() {
        return tilePosition;
    }

    public String toString() {
        return "from " + tilePosition + " to " + spacePosition;
    }
}

package com.barrybecker4.common.search.slidingpuzzle;

import com.barrybecker4.common.geometry.Location;

/**
 * Move from one board state to another by shifting 1 tile to the space position
 * @author Barry Becker
 */
public class Transition {

    private Location spacePosition;
    private Location tilePosition;

    Transition(Location spacePosition, Location tilePosition) {
        this.spacePosition = spacePosition;
        this.tilePosition = tilePosition;
    }

    Location getSpacePosition() {
        return spacePosition;
    }

    Location getTilePosition() {
        return tilePosition;
    }
}

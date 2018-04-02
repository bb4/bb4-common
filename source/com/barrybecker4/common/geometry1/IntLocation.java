// Copyright by Barry G. Becker, 2012. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.geometry1;

/**
 * Represents a location location of something in integer coordinates.
 * Immutable. Use MutableIntLocation if you really need to modify it (rare).
 *
 * @author Barry Becker
 */
public class IntLocation extends Location {

    private static final long serialVersionUID = 1;
    protected int row = 0;
    protected int col = 0;

    /**
     * Constructs a new point at (0, 0).
     * Default empty constructor
     */
    public IntLocation() {
    }

    public IntLocation(Location loc) {
        row = loc.getRow();
        col = loc.getCol();
    }

    /**
     * Constructs a new Location at the given coordinates.
     *
     * @param row  the row coordinate.
     * @param col  the column coordinate.
     */
    public IntLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public int getX() {
        return col;
    }

    @Override
    public int getY() {
        return row;
    }

    @Override
    public Location copy() {
        return new IntLocation(row, col);
    }

    @Override
    public Location incrementOnCopy(int rowChange, int colChange) {
        return new IntLocation(row + rowChange, col + colChange);
    }
}
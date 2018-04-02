// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.geometry1;

/**
 * Represents a location location of something in byte coordinates.
 * The range of bytes are only -127 to 127.
 *
 * Immutable. Use MutableIntLocation if you really need to modify it (rare).
 *
 * @author Barry Becker
 */
public class ByteLocation extends Location {

    private static final long serialVersionUID = 1;
    private byte row = 0;
    private byte col = 0;

    /**
     * Constructs a new Location at the given coordinates.
     *
     * @param row  the row coordinate (0 - 255).
     * @param col  the column coordinate (0 - 255).
     */
    public ByteLocation(int row, int col) {
        assert Math.abs(row) < 128 && Math.abs(col) < 128 : "row=" + row + " or col=" + col + " was out of range.";
        this.row = (byte) row;
        this.col = (byte) col;
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
        return new ByteLocation(row, col);
    }

    @Override
    public Location incrementOnCopy(int rowChange, int colChange) {
        return new ByteLocation(row + rowChange, col + colChange);
    }
}


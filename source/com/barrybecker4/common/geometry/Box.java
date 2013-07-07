/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.geometry;

/**
 * A box defined by 2 locations.
 * The coordinates have the resolution of integers.
 * @author Barry Becker
 */
public class Box {

    private IntLocation topLeftCorner_;
    private IntLocation bottomRightCorner_;

    /**
     * Constructor
     * Two points that define the box.
     * @param pt0 one corner of the box
     * @param pt1 the opposite corner of the box.
     */
    public Box(Location pt0, Location pt1) {

        this(Math.min(pt0.getRow(), pt1.getRow()), Math.min(pt0.getCol(), pt1.getCol()),
             Math.max(pt0.getRow(), pt1.getRow()), Math.max(pt0.getCol(), pt1.getCol()));
    }

    /**
     * Constructor
     * Two points that define the box.
     * @param pt0 one corner of the box
     * @param pt1 the opposite corner of the box.
     */
    public Box(IntLocation pt0, IntLocation pt1) {

        this(Math.min(pt0.getRow(), pt1.getRow()), Math.min(pt0.getCol(), pt1.getCol()),
             Math.max(pt0.getRow(), pt1.getRow()), Math.max(pt0.getCol(), pt1.getCol()));
    }

    /** Degenerate box consisting of a point in space */
    public Box(Location pt0) {
        this(pt0, pt0);
    }

    /**
     *  Constructor which takes the coordinates of any two diagonal corners.
     */
    public Box(int rowMin, int colMin, int rowMax, int colMax) {

        if (rowMin > rowMax) {
            int temp = rowMin;
            rowMin = rowMax;
            rowMax = temp;
        }
        if (colMin > colMax) {
            int temp = colMin;
            colMin = colMax;
            colMax = temp;
        }

        topLeftCorner_ = new IntLocation(rowMin, colMin);
        bottomRightCorner_ = new IntLocation(rowMax, colMax);
    }

    /**
     * Constructs a box with dimensions of oldBox, but expanded by the specified point
     * @param oldBox box to base initial dimensions on.
     * @param point point to expand new box by.
     */
    public Box(Box oldBox, Location point) {
        this(oldBox.getTopLeftCorner(), oldBox.getBottomRightCorner());
        expandBy(point);
    }

    /** @return the width of the box */
    public int getWidth() {
        return Math.abs(bottomRightCorner_.getCol() - topLeftCorner_.getCol());
    }

    /** @return the height of the box */
    public int getHeight() {
        return Math.abs(bottomRightCorner_.getRow() - topLeftCorner_.getRow());
    }

    public int getMaxDimension() {
        return Math.max(getWidth(), getHeight());
    }

    public IntLocation getTopLeftCorner() {
       return topLeftCorner_;
    }

    public IntLocation getBottomRightCorner() {
       return bottomRightCorner_;
    }

    public int getMinRow() {
        return topLeftCorner_.getRow();
    }

    public int getMinCol() {
        return topLeftCorner_.getCol();
    }

    public int getMaxRow() {
        return bottomRightCorner_.getRow();
    }

    public int getMaxCol() {
        return bottomRightCorner_.getCol();
    }

    public int getArea() {
        return getWidth() * getHeight();
    }

    /**
     * @param pt point to check for containment in the box.
     * @return true if the box contains the specified point
     */
    public boolean contains(Location pt) {
        int row = pt.getRow();
        int col = pt.getCol();
        return (row >= getMinRow() && row <= getMaxRow() && col >= getMinCol() && col <= getMaxCol());
    }

    /**
     * Note that the corner locations are immutable so we create new objects for them if they change.
     * @param loc location to expand out box by.
     */
    public void expandBy(Location loc) {
        expandBy(new IntLocation(loc));
    }

    /**
     * Note that the corner locations are immutable so we create new objects for them if they change.
     * @param loc location to expand out box by.
     */
    public void expandBy(IntLocation loc) {
        if (loc.getRow() < topLeftCorner_.getRow()) {
            topLeftCorner_ = new IntLocation(loc.getRow(), topLeftCorner_.getCol());
        }
        else if (loc.getRow() > bottomRightCorner_.getRow()) {
            bottomRightCorner_ = new IntLocation(loc.getRow(), bottomRightCorner_.getCol());
        }
        if (loc.getCol() < topLeftCorner_.getCol())  {
            topLeftCorner_ = new IntLocation(topLeftCorner_.getRow(), loc.getCol());
        }
        else if (loc.getCol() > bottomRightCorner_.getCol()) {
            bottomRightCorner_ = new IntLocation(bottomRightCorner_.getRow(), loc.getCol());
        }
    }

    /**
     * @param location the location to check if on board.
     * @return true if location is on this box's border
     */
    public boolean isOnEdge(Location location) {
        return (location.getRow() == bottomRightCorner_.getRow()
            || location.getRow() == topLeftCorner_.getRow()
            || location.getCol() == bottomRightCorner_.getCol()
            || location.getCol() == topLeftCorner_.getCol());
    }

    /**
     * @param location the location to check if on board.
     * @return true if location is on this box's border
     */
    public boolean isOnCorner(Location location) {
        return location.equals(bottomRightCorner_)
                || location.equals(topLeftCorner_)
                || ((location.getRow() == bottomRightCorner_.getRow()
                   && location.getCol() == topLeftCorner_.getCol())
                || (location.getRow() == topLeftCorner_.getRow()
                   && location.getCol() == bottomRightCorner_.getCol()));
    }

    /**
     * @param amount amount to expand all borders of the box by.
     * @param maxRow don't go further than this though.
     * @param maxCol don't go further than this though.
     */
    public void expandGloballyBy(int amount, int maxRow, int maxCol) {

        topLeftCorner_ =
                new IntLocation(Math.max(topLeftCorner_.getRow() - amount, 1),
                             Math.max(topLeftCorner_.getCol() - amount, 1));

        bottomRightCorner_ =
                new IntLocation(Math.min(bottomRightCorner_.getRow() + amount, maxRow),
                             Math.min(bottomRightCorner_.getCol() + amount, maxCol));
    }

    /**
     * @param threshold if withing this distance to the edge, extend the box all the way to that edge.
     * @param maxRow don't go further than this though.
     * @param maxCol don't go further than this though.
     */
    public void expandBordersToEdge(int threshold, int maxRow, int maxCol) {
        if (topLeftCorner_.getRow() <= threshold + 1) {
            topLeftCorner_ = new IntLocation(1, topLeftCorner_.getCol());
        }
        if (topLeftCorner_.getCol() <= threshold + 1) {
            topLeftCorner_ = new IntLocation(topLeftCorner_.getRow(), 1);
        }
        if (maxRow - bottomRightCorner_.getRow() <= threshold) {
            bottomRightCorner_ = new IntLocation(maxRow, bottomRightCorner_.getCol());
        }
        if (maxCol - bottomRightCorner_.getCol() <= threshold) {
            bottomRightCorner_ = new IntLocation(bottomRightCorner_.getRow(), maxCol);
        }
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("Box:"); //NON-NLS
        buf.append(topLeftCorner_);
        buf.append(" - ");
        buf.append(bottomRightCorner_);
        return buf.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Box box = (Box) o;

        return !(bottomRightCorner_ != null ? !bottomRightCorner_.equals(box.bottomRightCorner_) : box.bottomRightCorner_ != null)
            && !(topLeftCorner_ != null ? !topLeftCorner_.equals(box.topLeftCorner_) : box.topLeftCorner_ != null);
    }

    @Override
    public int hashCode() {
        int result = topLeftCorner_ != null ? topLeftCorner_.hashCode() : 0;
        result = 31 * result + (bottomRightCorner_ != null ? bottomRightCorner_.hashCode() : 0);
        return result;
    }

}

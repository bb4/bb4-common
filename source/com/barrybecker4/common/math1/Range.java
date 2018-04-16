/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math1;

/**
 * Manage a double precision range.
 *
 * @author Barry Becker
 */
public class Range {

    private double min;
    private double max;

    /** Default constructor */
    public Range() {
        min = Double.MAX_VALUE;
        max = -Double.MAX_VALUE;
    }

    /**
     * init with min and max values of the range.
     * @param minimum min value for range
     * @param maximum max value for range
     */
    public Range(double minimum, double maximum) {
        if (minimum > maximum) {
            throw new IllegalArgumentException("min greater than max");
        }
        min = minimum;
        max = maximum;
    }

    /** Copy constructor */
    public Range(Range range) {
        min = range.getMin();
        max = range.getMax();
    }

    /**
     * @return Returns the Max.
     */
    public double getMax() {
        return max;
    }

    /**
     * @return Returns the axisMin.
     */
    public double getMin() {
        return min;
    }

    /**
     *Extend this range by the range argument.
     */
    public void add(Range range) {
        add(range.getMin());
        add(range.getMax());
    }

    /**
     * Extend this range by the value argument.
     */
    public void add(double value) {
        if (value < min) {
            min = value;
        }
        if (value > max) {
            max = value;
        }
    }

    /**
     * The extend of the range.
     * @return the max minus the min.
     */
    public double getExtent() {
        if (min > max) {
            return Double.NaN;
        }
        return (max - min);
    }

    /**
     * @return true if the range is completely contained by us.
     */
    public boolean inRange(Range range) {
        return range.getMin() >= getMin() && range.getMax() <= getMax();
    }

    /**
     * @param value value to normalize on unit scale.
     * @return  normalized value assuming 0 for min. 1 for max.
     */
    public double mapToUnitScale(double value) {
        double range = getExtent();
        if (range == 0) {
            return 0;
        }
        return (value - getMin()) / getExtent();
    }

    public String toString() {
        return  this.getMin() + " to " + this.getMax(); //NON-NLS
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Range))
            return false;
        Range otherRange = (Range) obj;
        return (this.getMin() == otherRange.getMin() && this.getMax() == otherRange.getMax());
    }

    public int hashCode() {
        Double sum = (this.getMin() + this.getMax())/2.0;
        return sum.hashCode();
    }
}
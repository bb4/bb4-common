/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math1;

/**
 * A complex number range represents a box on the complex plane.
 * Immutable.
 *
 * @author Barry Becker
 */
public class ComplexNumberRange {

    private ComplexNumber point1;
    private ComplexNumber point2;
    private ComplexNumber extent;

    /**
     * init with min and max values of the range.
     * @param point1 one value for range
     * @param point2 other value for range
     */
    public ComplexNumberRange(ComplexNumber point1, ComplexNumber point2) {
        this.point1 = point1;
        this.point2 = point2;
        extent = this.point2.subtract(this.point1);
    }

    /**
     * @return point1.
     */
    public ComplexNumber getPoint1() {
        return point1;
    }

    /**
     * @return point2.
     */
    public ComplexNumber getPoint2() {
        return point2;
    }

    /**
     * If params are outside 0, 1, then the interpolated point will be outside the range.
     * @param realRatio between 0 and 1 in real direction
     * @param imaginaryRatio between 0 and 1 in imaginary direction
     * @return interpolated position.
     */
    public ComplexNumber getInterpolatedPosition(double realRatio, double imaginaryRatio) {

        return new ComplexNumber(point1.getReal() + extent.getReal() * realRatio,
                                 point1.getImaginary() + extent.getImaginary() * imaginaryRatio);
    }

    public String toString() {
        return getPoint1() + " to " + this.getPoint2(); //NON-NLS
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComplexNumberRange that = (ComplexNumberRange) o;

        return !(point1 != null ?
                !point1.equals(that.point1) :
                that.point1 != null) && !(point2 != null ? !point2.equals(that.point2) : that.point2 != null);

    }

    @Override
    public int hashCode() {
        int result = point1 != null ? point1.hashCode() : 0;
        result = 31 * result + (point2 != null ? point2.hashCode() : 0);
        return result;
    }

}
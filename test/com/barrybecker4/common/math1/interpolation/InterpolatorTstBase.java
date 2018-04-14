/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math1.interpolation;

import com.barrybecker4.common.math1.interplolation.Interpolator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Barry Becker
 */
public abstract class InterpolatorTstBase {

    private static final double EPS = 0.00000000000001;

    /** interpolation class under test. */
    protected Interpolator interpolator;


    protected abstract Interpolator createInterpolator(double[] func);

    @Test
    public void testSimpleInterpolation0_1() {
        double[] func = {0, 1, 2};
        interpolator = createInterpolator(func);

        double y = interpolator.interpolate(0.1);
        assertEquals("Unexpected y for 0.1", getExpectedSimpleInterpolation0_1(), y, EPS);

        y = interpolator.interpolate(0.9);
        assertEquals("Unexpected y for 0.9", getExpectedSimpleInterpolation0_9(), y, EPS);
    }

    protected abstract double getExpectedSimpleInterpolation0_1();
    protected abstract double getExpectedSimpleInterpolation0_9();

    @Test
    public void testTypicalInterpolate() {
        double[] func = {1, 2, 3, 4};
        interpolator = createInterpolator(func);

        double y = interpolator.interpolate(0.1);
        assertEquals("Unexpected y for 0.1", getExpectedTypicalInterpolation0_1(), y, EPS);


        y = interpolator.interpolate(0.9);
        assertEquals("Unexpected y for 0.9", getExpectedTypicalInterpolation0_9(), y, EPS);
    }

    protected abstract double getExpectedTypicalInterpolation0_1();
    protected abstract double getExpectedTypicalInterpolation0_4();
    protected abstract double getExpectedTypicalInterpolation0_5();
    protected abstract double getExpectedTypicalInterpolation0_9();


    @Test
    public void testInterpolateOnePoint() {
        double[] func = {1};
        interpolator = createInterpolator(func);

        double y = interpolator.interpolate(0.0);
        assertEquals("Unexpected y for 0.0", 1.0, y, EPS);
    }

    protected abstract double getExpectedOnePointInterpolation();

    @Test
    public void testInterpolate2Points() {
        double[] func = {0, 1};
        interpolator = createInterpolator(func);

        double y = interpolator.interpolate(0.1);
        assertEquals("Unexpected y for 0.1", getExpectedInterpolation2Points0_1(), y, EPS);
    }

    protected abstract double getExpectedInterpolation2Points0_1(); // 0.1

    @Test(expected = IllegalArgumentException.class)
    public void testInterpolateOutOfRangeClosePositive() {
        double[] func = {1, 2};
        interpolator = createInterpolator(func);
        interpolator.interpolate(1.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInterpolateOutOfRangeFar() {
        double[] func = {1, 2};
        interpolator = createInterpolator(func);
        interpolator.interpolate(2.1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInterpolateOutOfRangeNegative() {
        double[] func = {1, 2};
        interpolator = createInterpolator(func);
        interpolator.interpolate(-1.1);
    }
}

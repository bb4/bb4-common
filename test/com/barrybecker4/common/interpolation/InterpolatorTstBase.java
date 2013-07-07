/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.interpolation;

import com.barrybecker4.common.math.interplolation.Interpolator;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author Barry Becker
 */
public abstract class InterpolatorTstBase extends TestCase {

    private static final double EPS = 0.00000000000001;

    /** interpolation class under test. */
    protected Interpolator interpolator;


    protected abstract Interpolator createInterpolator(double[] func);

    /**  */
    public void testSimpleInterpolation0_1() {
        double[] func = {0, 1, 2};
        interpolator = createInterpolator(func);

        double y = interpolator.interpolate(0.1);
        Assert.assertEquals("Unexpected y for 0.1", getExpectedSimpleInterpolation0_1(), y, EPS);

        y = interpolator.interpolate(0.9);
        Assert.assertEquals("Unexpected y for 0.9", getExpectedSimpleInterpolation0_9(), y, EPS);
    }

    protected abstract double getExpectedSimpleInterpolation0_1();
    protected abstract double getExpectedSimpleInterpolation0_9();


    public void testTypicalInterpolate() {
        double[] func = {1, 2, 3, 4};
        interpolator = createInterpolator(func);

        double y = interpolator.interpolate(0.1);
        Assert.assertEquals("Unexpected y for 0.1", getExpectedTypicalInterpolation0_1(), y, EPS);


        y = interpolator.interpolate(0.9);
        Assert.assertEquals("Unexpected y for 0.9", getExpectedTypicalInterpolation0_9(), y, EPS);
    }

    protected abstract double getExpectedTypicalInterpolation0_1();
    protected abstract double getExpectedTypicalInterpolation0_4();
    protected abstract double getExpectedTypicalInterpolation0_5();
    protected abstract double getExpectedTypicalInterpolation0_9();



    public void testInterpolateOnePoint() {
        double[] func = {1};
        interpolator = createInterpolator(func);

        double y = interpolator.interpolate(0.0);
        Assert.assertEquals("Unexpected y for 0.0", 1.0, y, EPS);
    }

    protected abstract double getExpectedOnePointInterpolation();


    public void testInterpolate2Points() {
        double[] func = {0, 1};
        interpolator = createInterpolator(func);

        double y = interpolator.interpolate(0.1);
        Assert.assertEquals("Unexpected y for 0.1", getExpectedInterpolation2Points0_1(), y);
    }

    protected abstract double getExpectedInterpolation2Points0_1(); // 0.1

    public void testInterpolateOutOfRangeClosePositive() {
        double[] func = {1, 2};
        interpolator = createInterpolator(func);

        try {
            interpolator.interpolate(1.1);
            Assert.fail("Did not expect to get here");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            // Success
        }
        catch (AssertionError e)  {
            // Success  (this way if assertions are enabled).
        }
    }

    public void testInterpolateOutOfRangeFar() {
        double[] func = {1, 2};
        interpolator = createInterpolator(func);
        try {
            interpolator.interpolate(2.1);
            Assert.fail();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            // Success
        }
        catch (AssertionError e)  {
            // Success  (this way if assertions are enabled).
        }
    }

    public void testInterpolateOutOfRangeNegative() {
        double[] func = {1, 2};
        interpolator = createInterpolator(func);
        try {
            interpolator.interpolate(-1.1);
            Assert.fail();
        }
        catch (ArrayIndexOutOfBoundsException e) {
            // Success
        }
        catch (AssertionError e)  {
            // Success  (this way if assertions are enabled).
        }
    }
}

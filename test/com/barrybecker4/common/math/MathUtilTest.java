/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math;

import junit.framework.TestCase;

import javax.vecmath.Point2d;
//import static junit.framework.assertEquals;

/**
 * @author Barry Becker Date: Apr 2, 2006
 */
public class MathUtilTest extends TestCase {

    public void testPositiveGCD() {
        long result;

        result = MathUtil.gcd(2l, 4l);
        assertEquals(result, 2l);

        result = MathUtil.gcd(4l, 2l);
        assertEquals(result, 2l);

        result = MathUtil.gcd(420l, -40l);
        assertEquals(result, 20l);

        result = MathUtil.gcd(40l, 420l);
        assertEquals(result, 20l);
    }

    public void testNegativeGCD() {
        long result;

        result = MathUtil.gcd(2L, 0L);
        assertEquals(result, 2L);

        result = MathUtil.gcd(0L, 2L);
        assertEquals(result, 2L);

        result = MathUtil.gcd(423L, -40L);
        assertEquals(result, 1L);
    }

    public void testIntNeg() {
        assertEquals("1) ", 2, (int)2.1);
        assertEquals("2) ", 0, (int)(-0.1));
        assertEquals("3) ", -2, (int)(-2.1));
        assertEquals("4) ", -2, (int)(-2.9));
    }

    public void testFactorial() {
        assertEquals("Unexpected value for 4!", 24L, MathUtil.factorial(4));
    }

    public void testFactorialRatio4d3() {
        assertEquals("Unexpected value for 4!/3!)", 4L, MathUtil.permutation(4, 3));
    }

    public void testFactorialRatio7d4() {
        assertEquals("Unexpected value for 7!/4!)", 210L, MathUtil.permutation(7, 4));
    }

    public void testBigFactorialRatio7d4() {
        assertEquals("Unexpected value for 7!/4!)",
                "210", MathUtil.bigPermutation(7, 4).toString());
    }

    public void testBigFactorialRatio9d4() {
        assertEquals("Unexpected value for 9!/4!)",
                "15120", MathUtil.bigPermutation(9, 4).toString());
    }

    public void testBigFactorialRatio40d20() {
        assertEquals("Unexpected value for 40!/20!)",
                "335367096786357081410764800000",
                MathUtil.bigPermutation(40, 20).toString());
    }

    public void testBigFactorialRatio70d20() {
        assertEquals("Unexpected value for 70!/20!)",
                "4923573423718507525892570413923319470803578288313732111773416409792512000000000000",
                MathUtil.bigPermutation(70, 20).toString());
    }

    public void testCombination4_3() {
        assertEquals("Unexpected value for C(4, 3)", "4", MathUtil.combination(4, 3).toString());
    }

    public void testCombination40_30() {
        assertEquals("Unexpected value for C(40, 30)", "847660528", MathUtil.combination(40, 30).toString());
    }

    public void testFindAngle() {

        Point2d point = new Point2d(1.0, 1.0);
        for (double x = 0; x < 2.0 * Math.PI; x += 0.3) {
            Point2d toPoint = new Point2d(point.x + Math.cos(x), point.y + Math.sin(x));
            System.out.println("angle to " + toPoint +" is " + MathUtil.getDirectionTo(point, toPoint));
        }
        assertEquals("Unexpected angle.",
                Math.PI/4.0, MathUtil.getDirectionTo(point, new Point2d(2.0, 2.0)));
        assertEquals("Unexpected angle.",
                3.0*Math.PI/4.0, MathUtil.getDirectionTo(point, new Point2d(0.0, 2.0)));
        assertEquals("Unexpected angle.",
                5.0*Math.PI/4.0 - 2.0*Math.PI, MathUtil.getDirectionTo(point, new Point2d(0.0, 0.0)));
        assertEquals("Unexpected angle.",
                -Math.PI/4.0, MathUtil.getDirectionTo(point, new Point2d(2.0, 0.0)));
    }
}

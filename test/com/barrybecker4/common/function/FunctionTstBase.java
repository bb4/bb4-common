/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.function;

import com.barrybecker4.common.math.function.InvertibleFunction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Base test class for function classes.
 * @author Barry Becker
 */
public abstract class FunctionTstBase {

    private static final double EPS = 0.00000000000001;

    /** function class under test. */
    protected InvertibleFunction function;

    @Before
    public void setUp() {
        function = createFunction();
    }

    protected abstract InvertibleFunction createFunction();

    @Test
    public void testGetFunctionValue() {

        double y = function.getValue(0.1);
        assertEquals("Unexpected y for 0.1", getExpectedValue0_1(), y, EPS);

        y = function.getValue(0.9);
        assertEquals("Unexpected y for 0.9", getExpectedValue0_9(), y, EPS);
    }

    protected abstract double getExpectedValue0_1();
    protected abstract double getExpectedValue0_9();

    @Test
    public void testGetInverseFunctionValue() {

        double y = function.getInverseValue(0.1);
        assertEquals("Unexpected x for y=0.1", getExpectedInverseValue0_1(), y, EPS);

        y = function.getValue(0.9);
        assertEquals("Unexpected x for y=0.9", getExpectedInverseValue0_9(), y, EPS);
    }

    protected abstract double getExpectedInverseValue0_1();
    protected abstract double getExpectedInverseValue0_9();


    /*
    @Test
    public void testGetDomain() {

        Range range = function.getDomain();
        Range expRange = getExpectedDomain();
        Assert.assertTrue("Unexpected min for domain", expRange.equals(range));
    }

    @Test
    protected abstract Range getExpectedDomain();
    */
}
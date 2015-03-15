/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math.function;

import com.barrybecker4.common.math.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a general y values function. It does not have to be monotonic or 1-1.
 *
 * @author Barry Becker
 */
public class HeightFunction implements Function {

    private Range domain;
    private double[] yValues;

    private LinearFunction domainToBinFunc;


    /**
     * Constructor.
     * @param  domain the extent of the regularly spaced x axis values
     * @param yValues the y values.
     */
    public HeightFunction(Range domain, double[] yValues) {
        this.domain = domain;
        this.yValues = yValues;
        domainToBinFunc = new LinearFunction(domain, yValues.length  - 1);
    }

    /**
     * Constructor.
     * @param yValues the y values.  The length automatically determines the domain.
     */
    public HeightFunction(double[] yValues) {
        this.domain = new Range(0, yValues.length - 1);
        this.yValues = yValues;
        domainToBinFunc = new LinearFunction(domain, yValues.length  - 1);
    }

    /** X axis domain */
    @Override
    public Range getDomain() {
        return domain;
    }

    /**
     * @param xValue x value to get y value for.
     * @return y value
     */
    @Override
    public double getValue(double xValue) {
        return yValues[(int)(domainToBinFunc.getValue(xValue) + 0.5)];
    }
}


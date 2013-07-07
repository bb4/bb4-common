/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math.function;

import com.barrybecker4.common.math.Range;

/**
 * The function takes the log of a value in the specified base, then scales it.
 *
 * @author Barry Becker
 */
public class LogFunction implements InvertibleFunction {

    private static final double DEFAULT_BASE = 10;
    private double base;
    private double scale;
    private double baseConverter;
    private boolean positiveOnly;

    /**
     * Constructor.
     */
    public LogFunction(double scale) {
        this(DEFAULT_BASE, scale, false);
    }

    /**
     * Constructor.
     * @param base  logarithm base.
     * @param scale amount to scale after taking the logarithm.
     * @param positiveOnly if true then clamp negative log values at 0.
     */
    public LogFunction(double scale,  double base, boolean positiveOnly) {
        this.scale = scale;
        this.base = base;
        baseConverter = Math.log(base);
        this.positiveOnly = positiveOnly;
    }

    @Override
    public double getValue(double value) {

        if (value <= 0) {
            throw new IllegalArgumentException("Cannot take the log of a number (" + value + ") that is <=0");
        }
        double logValue = positiveOnly ?  Math.max(0, Math.log(value)) : Math.log(value);
        return  scale * logValue / baseConverter;
    }


    @Override
    public double getInverseValue(double value) {

        return Math.pow(base, value / scale);
    }

    @Override
    public Range getDomain() {
        return new Range(0, Double.MAX_VALUE);
    }

}
/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math.interplolation;

/**
 * @author Barry Becker
 */
public class StepInterpolator extends AbstractInterpolator {

    public StepInterpolator(double[] function) {
        super(function);
    }

    @Override
    public double interpolate(double value) {
        return function[(int)(value * function.length)];
    }

}
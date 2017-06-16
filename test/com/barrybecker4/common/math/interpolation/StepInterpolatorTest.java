/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math.interpolation;

import com.barrybecker4.common.math.interplolation.Interpolator;
import com.barrybecker4.common.math.interplolation.StepInterpolator;

/**
 * @author Barry Becker
 */
public class StepInterpolatorTest extends InterpolatorTstBase {

    @Override
    protected Interpolator createInterpolator(double[] func) {
          return new StepInterpolator(func);
    }

    @Override
    protected double getExpectedSimpleInterpolation0_1() {
        return 0.0;
    }

    @Override
    protected  double getExpectedSimpleInterpolation0_9() {
        return 2.0;
    }

    @Override
    protected double getExpectedTypicalInterpolation0_1() {
        return 1.0;
    }

    @Override
    protected double getExpectedTypicalInterpolation0_4() {
        return 2.296;
    }

    @Override
    protected double getExpectedTypicalInterpolation0_5() {
        return 2.5;
    }

    @Override
    protected double getExpectedTypicalInterpolation0_9() {
        return 4.0;
    }

    @Override
    protected double getExpectedOnePointInterpolation() {
        return 1.0;
    }


    @Override
    protected double getExpectedInterpolation2Points0_1() {
        return 0.0;
    }
}
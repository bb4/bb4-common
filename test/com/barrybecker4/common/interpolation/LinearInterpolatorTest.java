/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.interpolation;

import com.barrybecker4.common.math.interplolation.Interpolator;
import com.barrybecker4.common.math.interplolation.LinearInterpolator;

/**
 * @author Barry Becker
 */
public class LinearInterpolatorTest extends InterpolatorTstBase {

    @Override
    protected Interpolator createInterpolator(double[] func) {
          return new LinearInterpolator(func);
    }

    @Override
    protected double getExpectedSimpleInterpolation0_1() {
        return 0.2;
    }

    @Override
    protected  double getExpectedSimpleInterpolation0_9() {
        return 1.8;
    }

    @Override
    protected double getExpectedTypicalInterpolation0_1() {
        return 1.3;
    }

    @Override
    protected double getExpectedTypicalInterpolation0_4() {
        return 2.8;
    }

    @Override
    protected double getExpectedTypicalInterpolation0_5() {
        return 3.6;
    }

    @Override
    protected double getExpectedTypicalInterpolation0_9() {
        return 3.7;
    }

    @Override
    protected double getExpectedOnePointInterpolation() {
        return 1.0;
    }

    @Override
    protected double getExpectedInterpolation2Points0_1() {
        return 0.1;
    }
}
/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math1.interpolation;

import com.barrybecker4.common.math1.interplolation.HermiteInterpolator;
import com.barrybecker4.common.math1.interplolation.Interpolator;

/**
 * @author Barry Becker
 */
public class HermiteInterpolatorTest extends InterpolatorTstBase {

    @Override
    protected Interpolator createInterpolator(double[] func) {
          return new HermiteInterpolator(func);
    }

    @Override
    protected double getExpectedSimpleInterpolation0_1() {
        return 0.136;
    }

    @Override
    protected  double getExpectedSimpleInterpolation0_9() {
        return 1.864;
    }


    @Override
    protected double getExpectedTypicalInterpolation0_1() {
        return 1.2265;
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
        return 3.7735;
    }


    @Override
    protected double getExpectedOnePointInterpolation() {
        return 1.0;
    }


    @Override
    protected double getExpectedInterpolation2Points0_1() {
        return 0.064;
    }
}
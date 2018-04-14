/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.math1.function;

/**
 * @author Barry Becker
 */
public class ErrorFunctionTest extends FunctionTstBase {

    @Override
    protected InvertibleFunction createFunction() {
        return new ErrorFunction();
    }

    @Override
    protected double getExpectedValue0_1() {
        return 0.11246296000000001;
    }

    @Override
    protected double getExpectedValue0_9() {
        return 0.7969081;
    }


    @Override
    protected double getExpectedInverseValue0_1() {
        return 0.089;
    }

    @Override
    protected double getExpectedInverseValue0_9() {
        return 0.7969081;
    }

}
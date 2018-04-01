// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.expression1;

/**
 * The expected binary operators in the text expression.
 * @author Barry Becker
 */
public interface Operator  {

    char getSymbol();

    double operate(double operand1, double operand2);
}
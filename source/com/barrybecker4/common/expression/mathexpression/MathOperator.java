// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.expression.mathexpression;

import com.barrybecker4.common.expression.Operator;

/**
 * The expected binary operators in the text expression.
 * @author Barry Becker
 */
public enum MathOperator implements Operator {

    PLUS('+'),
    MINUS('-'),
    TIMES('*'),
    DIVIDE('/'),
    EXPONENT('^');

    private char symbol;

    MathOperator(char c)  {
        symbol = c;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }


    @Override
    public double operate(double operand1, double operand2) {
        double result;
        switch (this) {
            case PLUS : result = operand1 + operand2; break;
            case MINUS : result = operand1 - operand2; break;
            case TIMES : result = operand1 * operand2; break;
            case DIVIDE : result = operand1 / operand2; break;
            case EXPONENT : result = Math.pow(operand1, operand2); break;
            default : throw new Error("Unexpected operator :" + getSymbol());
        }
        return result;
    }

}
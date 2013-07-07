// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.expression;

/**
 * @author Barry Becker
 */
public enum Tokens {

    PLUS('+'),
    MINUS('-'),
    LEFT_PAREN('('),
    RIGHT_PAREN(')');

    private char symbol;

    Tokens(char c) {
       symbol = c;
    }

    public char getSymbol() {
        return symbol;
    }

}

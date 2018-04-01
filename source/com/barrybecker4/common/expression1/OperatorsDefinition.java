// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.expression1;

import java.util.List;

/**
 * The expected binary operators in the text expression.
 * @author Barry Becker
 */
public interface OperatorsDefinition {

    boolean isOperator(char c);

    /**
     * Defines the order of precedence for the operators
     * This at the same level are evaluated from left to right.
     */
    Operator[][] getOperatorPrecedence();

    boolean isLastNodeOperator(List<TreeNode> nodes);
}
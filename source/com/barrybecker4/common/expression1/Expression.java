// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.expression1;

/**
 * Creates a tree from a text representation of an expression that
 * is written in terms of x.
 * @author Barry Becker
 */
public class Expression {

    /** root of the binary tree representing the expression */
    private TreeNode rootNode;
    private boolean isValid = false;

    /**
     * Constructor
     * @param expressionText the expression in text form. It will be parsed.
     */
    public Expression(String expressionText, ExpressionParser parser) {

        try {
           rootNode = parser.parse(expressionText);
           isValid = true;
        }
        catch (Exception e) {
            isValid = false;
            e.printStackTrace();
        }
    }

    public boolean isValid() {
        return this.isValid;
    }

    public TreeNode getRootNode() {
        return rootNode;
    }
}

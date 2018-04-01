// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.expression1;

import java.util.LinkedList;
import java.util.List;

/**
 * A node in a binary tree.
 * Contains either a operator (non-leaf) or operand (at leaf).
 * @author Barry Becker
 */
public class TreeNode {

    /** child nodes if any */
    public List<TreeNode> children = new LinkedList<>();

    /** if true then the sup expression represented by this node has parenthesis around it */
    public boolean hasParens;

    /** either an operator or an operand */
    private String data;

    private OperatorsDefinition opDef;


    /**
     * Constructor
     * @param  value data value - either an operator or an operand.
     */
    public TreeNode(String value, OperatorsDefinition opDef) {
        data = value;
        this.opDef = opDef;
    }

    /**
     * Constructor
     * @param  value data value - either an operator or an operand.
     */
    public TreeNode(char value, OperatorsDefinition opDef) {
        data = Character.toString(value);
        this.opDef = opDef;
    }

    public String getData() {
        return data;
    }

    public String toString() {
        return data;
    }

    /** @return true if the specified node is an operator */
    public boolean isOperator() {
        return !hasParens && data.length() == 1 && opDef.isOperator(data.charAt(0));
    }
}


// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.expression1;

import java.util.List;

import static com.barrybecker4.common.expression1.Tokens.LEFT_PAREN;
import static com.barrybecker4.common.expression1.Tokens.RIGHT_PAREN;

/**
 * Parses the text form of an expression into a tree representation.
 * See derived classes for specific sorts of expression parsers.
 * @author Barry Becker
 */
public abstract class ExpressionParser {

    protected OperatorsDefinition opDef;

    public ExpressionParser(OperatorsDefinition opDef) {
        this.opDef = opDef;
    }

    /**
     * Parses an expression.
     * Called recursively to parse sub-expressions nested within parenthesis.
     * @param textExpression the expression to parse. Must not be null or empty.
     * @return the root node in the parsed expression tree.
     */
    public TreeNode parse(String textExpression) {

        List<TreeNode> nodes = getNodesAtLevel(textExpression);
        return makeTreeFromNodes(nodes);
    }

    /**
     * Recursive method to find all the tree nodes for the terms at the current parenthesized level.
     * For example, given this expression
     * 2x^3 +  5(x + 3x^2) / (x - 1)
     * the items in []'s represent the array of nodes returned.
     * [2] [*] [x] [^] [3] [+] [5][*][x + 3x^2] [/] [x - 1]
     * The parts that were in ()'s become their own subtrees via recursive calls.
     *
     * @param exp the expression to get the nodes at the current level for
     * @return array of nodes representing terms that the current level.
     * @throws Error if there is a syntax error causing the expression to be invalid
     */
    abstract protected List<TreeNode> getNodesAtLevel(String exp);

    /**
     * @param exp the whole sup expression
     * @param pos location of lef parenthesis
     * @return location of matching right parenthesis
     */
    protected int findClosingParen(String exp, int pos) {
        int parenCount = 1;
        int i = pos;
        char ch;

        do {
            ch = exp.charAt(i++);
            if (ch == LEFT_PAREN.getSymbol()) parenCount++;
            if (ch == RIGHT_PAREN.getSymbol()) parenCount--;
        } while (!(ch == RIGHT_PAREN.getSymbol() && parenCount == 0) && i < exp.length());

        if (ch != RIGHT_PAREN.getSymbol() && i == exp.length()) {
            throw new IllegalStateException("Mismatched parenthesis in " + exp);
        }
        return i-1;
    }

    /**
     * Parse a parenthesized sub expression recursively.
     * @return current token. May have been reset to "".
     */
    protected abstract String processSubExpression(
        String exp, int pos, String token, int closingParenPos, List<TreeNode> nodes);

    /**
     * The token may represent several nodes because of implicit multiplication.
     * For example,
     *   -4x should become  [-4] [times] [x]
     *    -x should become [-1] [times] [x]
     * @param token the token to parse
     * @param nodes array of nodes that the token was parsed into.
     */
    protected abstract void pushNodesForToken(String token, List<TreeNode> nodes);


    /**
     * Converts a list of nodes to a single node by reducing them to
     * subtrees in order of operator precedence.
     */
    protected abstract TreeNode makeTreeFromNodes(List<TreeNode> nodes);

    protected void splice(List<TreeNode> nodes, int start, int num, TreeNode newNode) {
        for (int i = start; i < start + num; i++) {
            nodes.remove(start);
        }
        nodes.add(start, newNode);
    }


    protected boolean isOperator(TreeNode node, Operator[] ops) {
        if (node.getData() == null || node.getData().length() != 1) return false;
        return  opDef.isOperator(node.getData().charAt(0));
    }
}

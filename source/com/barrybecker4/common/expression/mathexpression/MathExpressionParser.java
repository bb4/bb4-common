// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.expression.mathexpression;

import com.barrybecker4.common.expression.ExpressionParser;
import com.barrybecker4.common.expression.Operator;
import com.barrybecker4.common.expression.OperatorsDefinition;
import com.barrybecker4.common.expression.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.barrybecker4.common.expression.Tokens.LEFT_PAREN;
import static com.barrybecker4.common.expression.Tokens.RIGHT_PAREN;

/**
 * Parses the text form of an expression (in x) into a tree representation.
 * Create a subclass for parsing a math expression.
 * @author Barry Becker
 */
public class MathExpressionParser extends ExpressionParser {

    public MathExpressionParser(OperatorsDefinition opDef) {
        super(opDef);
    }

    /**
     * Recursive method to find all the tree nodes for the terms a the current level.
     * For example, given this expression
     * 2x^3 +  5(x + 3x^2) / (x - 1)
     * the items in []'s represent the array of nodes returned.
     * [2] [*] [x] [^] [3] [+] [5][*][x + 3x^2] [/] [x - 1]
     * The parts that were in {()'s become their own subtrees via recursive calls.
     *
     * @param exp the expression to get the nodes at the current level for
     * @return array of nodes representing terms that the current level.
     * @throws Error if there is a syntax error causing the expression to be invalid
     */
    @Override
    protected List<TreeNode> getNodesAtLevel(String exp) {

        int pos= 0;
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        String token = "";
        char ch = exp.charAt(pos);

        while (pos < exp.length() && !token.equals("" + RIGHT_PAREN.getSymbol())) {
            if (ch == ' ') {
                // spaces are ignored
            }
            else if (ch == LEFT_PAREN.getSymbol()) {
                int closingParenPos = findClosingParen(exp, pos + 1);
                // this method will make the recursive call
                token = processSubExpression(exp, pos + 1, token, closingParenPos, nodes);
                pos = closingParenPos + 1;
            }
            else if (ch == MathOperator.MINUS.getSymbol() && token.length() == 0 && opDef.isLastNodeOperator(nodes)) {
                // a leading minus sign
                token += ch;
            }
            else if (isNumericChar(ch)) {
                token += ch;
                if (token.contains("x")) { //NON-NLS
                    throw new Error("Cannot have numbers after x in a term "+ token +" within " + exp);
                }
            }
            else if (ch == 'x') {
                token += ch;
            }
            else if (opDef.isOperator(ch)) {
                pushNodesForToken(token, nodes);
                token = "";
                nodes.add(new TreeNode(ch, opDef));
            }
            else {
                throw new Error("Unexpected character " + ch +" in expression: " + exp);
            }
            pos++;
            if (pos < exp.length()) {
                ch = exp.charAt(pos);
            }
        }
        // add the final node
        pushNodesForToken(token, nodes);

        return nodes;
    }


    /**
     * Parse a parenthesized sub expression recursively.
     * @return current token. May have been reset to "".
     */
    @Override
    protected String processSubExpression(
        String exp, int pos, String token, int closingParenPos, List<TreeNode> nodes) {

        // recursive call for sub expression
        String subExp = exp.substring(pos, closingParenPos);
        TreeNode subTree = parse(subExp);
        subTree.hasParens = true;

        if (token != null && token.length() > 0) {
            // there was some leading token before the parenthesized expression.
            pushNodesForToken(token, nodes);
            token = "";
            nodes.add(new TreeNode(MathOperator.TIMES.getSymbol(), opDef));
        }
        else if (!nodes.isEmpty() && nodes.get(nodes.size() - 1).hasParens) {
            nodes.add(new TreeNode(MathOperator.TIMES.getSymbol(), opDef));
        }
        nodes.add(subTree);
        return token;
    }

    /**
     * The token may represent several nodes because of implicit multiplication.
     * For example,
     *   -4x should become  [-4] [times] [x]
     *    -x should become [-1] [times] [x]
     * @param token the token to parse
     * @param nodes array of nodes that the token was parsed into.
     */
    @Override
    protected void pushNodesForToken(String token, List<TreeNode> nodes) {

        if (token == null || token.length() == 0) return;

        int len = token.length();
        if (token.charAt(len - 1) == 'x') {
            if (len > 1) {
                if (token.charAt(0) == MathOperator.MINUS.getSymbol()) {
                    nodes.add(new TreeNode("-1", opDef));
                }
                else {
                    nodes.add(getNodeForNumber(token.substring(0, len - 1)));
                }
                nodes.add(new TreeNode(MathOperator.TIMES.getSymbol(), opDef));
            }
            nodes.add(new TreeNode("x", opDef)); //NON-NLS
        }
        else {
            nodes.add(getNodeForNumber(token));
        }
    }

    /**
     * Converts a list of nodes to a single node by reducing them to
     * subtrees in order of operator precedence.
     */
    @Override
    protected TreeNode makeTreeFromNodes(List<TreeNode> nodes) {

        for (Operator[] ops : opDef.getOperatorPrecedence()) {
            System.out.println("nodes=" + nodes + " ops="+ Arrays.toString(ops)); //NON-NLS
            nodes = reduceNodes(ops, nodes);
        }

        if (nodes.size() > 1) {
            throw new Error("Expected to have only one node after reducing, but have "
                + nodes.size() +" : " + nodes);
        }
        if (nodes.get(0).isOperator() && nodes.get(0).children.size() == 0) {
            throw new Error("Missing operands");
        }

        return nodes.get(0);
    }

    /**
     * Simplify the list of terms by evaluating the terms joined by the specified operators.
     * Reduce the nodes list to a single node and return it.
     * @param ops list of operators that all have the same precedence.
     * @param nodes the list of nodes to reduce
     * @return same list of nodes, but reduced.
     */
    private List<TreeNode> reduceNodes(Operator[] ops, List<TreeNode> nodes) {

        int index = 1;
        if (nodes.size() == 2) {
            throw new Error("Missing operand : " + nodes);
        }

        while (index < nodes.size()) {
            if (isOperator(nodes.get(index), ops)) {
                nodes.get(index).children = Arrays.asList(nodes.get(index - 1), nodes.get(index + 1));
                if (nodes.size() < index + 1) {
                    throw new Error("Not enough operands for operator in nodes=" + nodes);
                }
                //System.out.println("before splice : " + nodes);
                splice(nodes, index-1, 3, nodes.get(index));
                //System.out.println("after splice : " + nodes);
            } else {
                index += 2;
            }
        }
        return nodes;
    }

    private boolean isNumericChar(char ch) {

        return (ch >= '0' && ch <= '9') || ch == '.';
    }

    private TreeNode getNodeForNumber(String token) {
        double num = Double.parseDouble(token);
        if (Double.isNaN(num)) {
            throw new IllegalStateException("Invalid number in expression: " + token);
        }
        return new TreeNode("" + num, opDef);
    }
}

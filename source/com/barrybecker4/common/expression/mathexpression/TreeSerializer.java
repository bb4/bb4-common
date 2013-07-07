// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.common.expression.mathexpression;

import com.barrybecker4.common.expression.TreeNode;

import static com.barrybecker4.common.expression.Tokens.LEFT_PAREN;
import static com.barrybecker4.common.expression.Tokens.RIGHT_PAREN;

/**
 * Turns a tree into a string via in order traversal.
 * Implements visitor pattern
 *
 * @author Barry Becker
 */
public class TreeSerializer {


    public String serialize(TreeNode node) {

        String serialized = "";

        if (node != null) {
            serialized = traverse(node);
        }
        return serialized.length()>0 ? serialized : "Invalid"; //NON-NLS
    }

    /** processing for inner nodes */
    private String traverse(TreeNode node) {

        String text = "";
        if (node.children.size() == 2) {
            text += (node.hasParens ? LEFT_PAREN.getSymbol() : "") + traverse(node.children.get(0));
            text += " "  + node.getData() + " ";
            text += traverse(node.children.get(1)) + (node.hasParens ? RIGHT_PAREN.getSymbol():"");
        }
        else {
            text += node.getData();
        }
        return text;
    }
}

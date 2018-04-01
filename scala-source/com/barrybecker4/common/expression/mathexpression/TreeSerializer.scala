/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.expression.mathexpression

import com.barrybecker4.common.expression.{LEFT_PAREN, RIGHT_PAREN}
import com.barrybecker4.common.expression.TreeNode

/**
  * Turns a tree into a string via in order traversal.
  * Implements visitor pattern
  * @author Barry Becker
  */
class TreeSerializer {
  def serialize(node: TreeNode[MathOperator]): String = {
    var serialized = ""
    if (node != null) serialized = traverse(node)
    if (serialized.length > 0) serialized
    else "Invalid"
  }

  /** processing for inner nodes */
  private def traverse(node: TreeNode[MathOperator]): String = {
    var text = ""
    if (node.children.size == 2) {
      text += (if (node.hasParens) LEFT_PAREN.symbol else "") + traverse(node.children.head)
      text += " " + node.getData + " "
      text += traverse(node.children(1)) + (if (node.hasParens) RIGHT_PAREN.symbol else "")
    }
    else text += node.getData
    text
  }
}
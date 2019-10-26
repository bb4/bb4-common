/* Copyright by Barry G. Becker, 2000-2019. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.expression

/**
  * Turns a tree into a string via in order traversal.
  * Implements visitor pattern
  * @author Barry Becker
  */
class TreeSerializer {

  def serialize(node: TreeNode): String = {
    var serialized = ""
    if (node != null) serialized = traverse(node)
    if (serialized.length > 0) serialized
    else "Invalid"
  }

  /** processing for inner nodes */
  private def traverse(node: TreeNode): String = {
    var text = ""
    if (node.children.size == 2) {
      val lsymb = if (node.hasParens) LEFT_PAREN.symbol else ""
      val rsymb = if (node.hasParens) RIGHT_PAREN.symbol else ""
      text += s"$lsymb${traverse(node.children.head)}"
      text += s" ${node.getData} "
      text += traverse(node.children(1)) + rsymb
    }
    else text += node.getData
    text
  }
}

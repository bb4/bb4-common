/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.expression


/**
  * A node in a binary tree.
  * Contains either a operator (non-leaf) or operand (at leaf).
  *
  * @author Barry Becker
  */
class TreeNode {
  /** child nodes if any */
  var children: Seq[TreeNode] = Seq[TreeNode]()

  /** if true then the sup expression represented by this node has parenthesis around it */
  var hasParens = false

  /** either an operator or an operand */
  private var data: String = _
  private var opDef: OperatorsDefinition = _

  /** Constructor
    * @param  value data value - either an operator or an operand.
    */
  def this(value: String, opDef: OperatorsDefinition) {
    this()
    data = value
    this.opDef = opDef
  }

  def this(value: Char, opDef: OperatorsDefinition) {
    this()
    data = Character.toString(value)
    this.opDef = opDef
  }

  def getData: String = data

  override def toString: String = data

  /** @return true if the specified node is an operator */
  def isOperator: Boolean = !hasParens && data.length == 1 && opDef.isOperator(data.charAt(0))
}


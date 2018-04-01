/*
 * Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT
 */
package com.barrybecker4.common.expression


/**
  * Creates a tree from a text representation of an expression that is written in terms of x.
  * @param expressionText the expression in text form. It will be parsed.
  * @author Barry Becker
  */
class Expression[T <: Operator](val expressionText: String, val parser: ExpressionParser[T]) {

  /** root of the binary tree representing the expression */
  private var rootNode: TreeNode[T] = _
  private var valid = false

  def isValid: Boolean = this.valid
  def getRootNode: TreeNode[T] = rootNode

  try {
    rootNode = parser.parse(expressionText)
    valid = true
  } catch {
    case e: Exception =>
      valid = false
      e.printStackTrace()
  }

}
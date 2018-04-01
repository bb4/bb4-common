/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.expression

import scala.collection.mutable.ListBuffer


/**
  * The expected binary operators in the text expression.
  * @author Barry Becker
  */
trait OperatorsDefinition[T <: Operator] {

  def isOperator(c: Char): Boolean

  /**
    * Defines the order of precedence for the operators
    * This at the same level are evaluated from left to right.
    */
  def getOperatorPrecedence: Array[Array[T]]

  def isLastNodeOperator(nodes: ListBuffer[TreeNode[T]]): Boolean
}
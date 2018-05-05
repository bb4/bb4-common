/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.expression.mathexpression

import com.barrybecker4.common.expression.{Operator, OperatorsDefinition, TreeNode}
import scala.collection.mutable.ListBuffer


object MathOperatorsDefinition {

  /** Defines the order of precedence for the operators
    * This at the same level are evaluated from left to right.
    */
  private val OPERATOR_PRECEDENCE = Array(
    Array[Operator](EXPONENT),
    Array[Operator](TIMES, DIVIDE),
    Array[Operator](PLUS, MINUS)
  )
}

/**
  * The expected binary operators in the text expression.
  * @author Barry Becker
  */
class MathOperatorsDefinition extends OperatorsDefinition {

  override def getOperatorPrecedence: Array[Array[Operator]] = MathOperatorsDefinition.OPERATOR_PRECEDENCE

  /** @return true if the specified character is an operator */
  override def isOperator(ch: Char): Boolean =
    ch == PLUS.symbol || ch == MINUS.symbol || ch == TIMES.symbol || ch == DIVIDE.symbol || ch == EXPONENT.symbol

  /** @return true if the last node is an operator or there were no previous nodes  */
  override def isLastNodeOperator(nodes: ListBuffer[TreeNode]): Boolean =
    nodes.isEmpty || nodes.last.isOperator
}
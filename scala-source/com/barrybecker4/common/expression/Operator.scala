/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.expression

/**
  * The expected binary operators in the text expression.
  * @author Barry Becker
  */
abstract class Operator(val symbol: Char) {
  def operate(operand1: Double, operand2: Double): Double
}
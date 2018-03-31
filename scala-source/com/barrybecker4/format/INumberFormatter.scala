/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.format

/**
  * Knows how to format something.
  * @author Barry Becker
  */
trait INumberFormatter {
  def format(number: Double): String
}

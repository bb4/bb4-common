/** Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.format

final class DefaultNumberFormatter extends INumberFormatter {
  inline override def format(number: Double): String =
    NumberFormatter.default.format(number)
}

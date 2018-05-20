/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common

package object testsupport {

  /** @param s string to process
    * @return the string without the leading margin char and with unix style line endings
    */
  def strip(s: String): String =
    s.stripMargin.replaceAll(System.lineSeparator, "\n")
}

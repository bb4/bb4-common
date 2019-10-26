/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common

class Watch {
  final private val start = System.currentTimeMillis

  /** @return the elapsed time (in seconds) since this object was created. */
  def getElapsedTime: Double = (System.currentTimeMillis - start) / 1000.0
}

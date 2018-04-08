/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.app

import java.io.FileNotFoundException


/**
  * Log to memory only. Mostly used in unit tests, but could have other uses.
  * @author Barry Becker
  */
class MemoryLogger(sbuilder: Option[StringBuilder] = None) extends ILog {
  private var bldr: StringBuilder = if (sbuilder.isDefined) sbuilder.get else null

  override def setDestination(logDestination: Int): Unit = {}
  override def getDestination = 0

  @throws[FileNotFoundException]
  override def setLogFile(fileName: String): Unit = {}
  override def setStringBuilder(bldr: StringBuilder): Unit = {this.bldr = bldr}
  override def print(logLevel: Int, appLogLevel: Int, message: String): Unit = {}
  override def println(logLevel: Int, appLogLevel: Int, message: String): Unit = {}
  override def print(message: String): Unit = { bldr.append(message) }
  override def println(message: String): Unit = { bldr.append(message).append("\n")}
  override def toString: String = bldr.toString
}

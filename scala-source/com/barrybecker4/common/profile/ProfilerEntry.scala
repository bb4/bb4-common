/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.profile

import com.barrybecker4.common.app.ILog
import com.barrybecker4.common.format.FormatUtil


object ProfilerEntry {
  private val INDENT = "    "
}

/**
  * Represents the timing numbers for a named region of the code.
  * @param name the name of this profiler entry
  * @author Barry Becker
  */
class ProfilerEntry(val name: String) {
  private var startTime: Long = 0
  /** the total time used by this named code section while the app was running  */
  private var totalTime: Long = 0
  final private var children = List[ProfilerEntry]()

  def addChild(child: ProfilerEntry): Unit =
    children :+= child

  def start(): Unit =
    startTime = System.currentTimeMillis

  def stop(): Unit =
    totalTime += System.currentTimeMillis - startTime

  def getTime: Long = totalTime
  def getTimeInSeconds: Double = totalTime.toDouble / 1000.0

  def resetAll(): Unit = {
    totalTime = 0
    for (p <- children) p.resetAll()
  }

  def print(): Unit = print("", null)

  def print(indent: String, logger: ILog): Unit = {
    val text = indent + getFormattedTime
    if (logger == null) println(text) else logger.println(text)
    var totalChildTime: Long = 0

    for (pe <- children) {
      totalChildTime += pe.getTime
      pe.print(indent + ProfilerEntry.INDENT, logger)
    }
    assert(totalChildTime <= totalTime,
      "The sum of the child times(" + totalChildTime + ") cannot be greater than the parent time (" +
        totalTime + ") for entry '" + name + "'. " + "child entries =" + children)
  }

  override def toString: String = getFormattedTime

  private def getFormattedTime = {
    val seconds = getTimeInSeconds
    "Time for " + name + " : " + FormatUtil.formatNumber(seconds) + " seconds"
  }
}
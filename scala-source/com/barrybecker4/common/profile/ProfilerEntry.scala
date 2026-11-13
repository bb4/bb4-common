/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.common.profile

import com.barrybecker4.common.app.{AppContext, ILog}
import com.barrybecker4.common.format.FormatUtil


object ProfilerEntry {
  private val INDENT = "    "
  private val defaultClock: () => Long = System.currentTimeMillis
}

/**
  * Represents the timing numbers for a named region of the code.
  * @param name the name of this profiler entry
  * @param clock time source in milliseconds; override in tests
  */
class ProfilerEntry(val name: String, clock: () => Long = ProfilerEntry.defaultClock) {
  private var startTime: Long = 0
  private var running: Boolean = false
  /** the total time used by this named code section while the app was running  */
  private var totalTime: Long = 0
  private var children = List[ProfilerEntry]()

  def addChild(child: ProfilerEntry): Unit =
    children :+= child

  def start(): Unit = {
    startTime = clock()
    running = true
  }

  /** No-op if [[start]] was not called; avoids attributing time since epoch 0. */
  def stop(): Unit = {
    if (!running) return
    totalTime += clock() - startTime
    running = false
  }

  def getTime: Long = totalTime
  def getTimeInSeconds: Double = totalTime.toDouble / 1000.0
  def isRunning: Boolean = running

  def resetAll(): Unit = {
    totalTime = 0
    startTime = 0
    running = false
    children.foreach(_.resetAll())
  }

  def print(): Unit = print("", None)

  def print(indent: String, logger: Option[ILog]): Unit = {
    logLine(indent + formattedTime, logger)
    children.foreach(_.print(indent + ProfilerEntry.INDENT, logger))
    validateChildTimes()
  }

  /** Throws if any child's recorded time exceeds this entry's total. */
  def validateChildTimes(): Unit = {
    val totalChildTime = children.map(_.getTime).sum
    if (totalChildTime > totalTime) {
      throw new IllegalStateException(
        s"The sum of the child times($totalChildTime) cannot be greater than the parent time " +
          s"($totalTime) for entry '$name'. child entries =$children"
      )
    }
  }

  override def toString: String = formattedTime

  private def formattedTime: String = {
    val seconds = getTimeInSeconds
    AppContext.getLabel("PROFILER_TIME_LINE", Array(name, FormatUtil.formatNumber(seconds)))
  }

  private def logLine(text: String, logger: Option[ILog]): Unit =
    logger.fold(println(text))(_.println(text))
}

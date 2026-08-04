/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.profile

import com.barrybecker4.common.app.ILog


/**
  * Use this class to get performance numbers for your application to help eliminate bottlenecks.
  * It is typically subclassed in order to identify (using entries) the specific parts
  * of a program that need to be profiled.
  * @author Barry Becker
  */
class Profiler() {
  private var hmEntries = Map[String, ProfilerEntry]()
  private var topLevelEntries = List[ProfilerEntry]()
  private var enabled = true
  private var logger: Option[ILog] = None

  /** add a top level entry.
    * @param name of the top level entry
    */
  def add(name: String): Unit = {
    val entry = new ProfilerEntry(name)
    topLevelEntries :+= entry
    hmEntries += name -> entry
  }

  /** Add an entry into the profiler entry hierarchy.
    * @param name   name for new entry
    * @param parent entry above us.
    */
  def add(name: String, parent: String): Unit = {
    val par = getEntry(parent)
    val e = new ProfilerEntry(name)
    par.addChild(e)
    hmEntries += name -> e
  }

  /** @param name the entry for whom we are to start the timing */
  def start(name: String): Unit = {
    if (!enabled) return
    val p = getEntry(name)
    p.start()
  }

  /** @param name the entry for which we are to stop the timing and increment the total time */
  def stop(name: String): Unit = {
    if (!enabled) return
    val p = getEntry(name)
    p.stop()
  }

  def getEntry(name: String): ProfilerEntry = hmEntries(name)

  /** reset all the timing numbers to 0 */
  def resetAll(): Unit =
    for (entry <- topLevelEntries) entry.resetAll()

  /** Pretty print all the performance statistics. */
  def print(): Unit = {
    if (!enabled) return
    for (entry <- topLevelEntries) entry.print("", logger.orNull)
  }

  /** turn on/off profiling */
  def setEnabled(enable: Boolean): Unit =
    enabled = enable

  def isEnabled: Boolean = enabled

  def setLogger(logger: ILog): Unit =
    this.logger = Option(logger)

  def printMessage(message: String): Unit =
    logger match {
      case Some(log) => log.print(message)
      case None => println(message)
    }
}
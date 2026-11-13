/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.profile

import com.barrybecker4.common.app.ILog


/**
  * Use this class to get performance numbers for your application to help eliminate bottlenecks.
  * It is typically subclassed in order to identify (using entries) the specific parts
  * of a program that need to be profiled.
  *
  * '''Thread-safety:''' This class is '''not thread-safe'''. Each Profiler instance should be
  * used by a single thread, or externally synchronized if shared across threads. The internal
  * collections are immutable, but updates via `var` reassignment are not atomic and will produce
  * race conditions under concurrent access.
  */
class Profiler() {
  private var hmEntries = Map[String, ProfilerEntry]()
  private var topLevelEntries = List[ProfilerEntry]()
  private var enabled = true
  private var logger: Option[ILog] = None

  /** add a top level entry.
    * @param name of the top level entry
    * @throws IllegalArgumentException if an entry with this name already exists
    */
  def add(name: String): Unit = {
    requireUniqueName(name)
    val entry = new ProfilerEntry(name)
    topLevelEntries :+= entry
    hmEntries += name -> entry
  }

  /** Add an entry into the profiler entry hierarchy.
    * @param name   name for new entry
    * @param parent entry above us.
    * @throws IllegalArgumentException if name already exists or parent is unknown
    */
  def add(name: String, parent: String): Unit = {
    requireUniqueName(name)
    val par = getEntry(parent)
    val e = new ProfilerEntry(name)
    par.addChild(e)
    hmEntries += name -> e
  }

  /** @param name the entry for whom we are to start the timing */
  def start(name: String): Unit = {
    if (!enabled) return
    getEntry(name).start()
  }

  /** @param name the entry for which we are to stop the timing and increment the total time */
  def stop(name: String): Unit = {
    if (!enabled) return
    getEntry(name).stop()
  }

  /** @throws IllegalArgumentException if no entry with this name was added */
  def getEntry(name: String): ProfilerEntry =
    hmEntries.getOrElse(name, throw new IllegalArgumentException(s"No profiler entry named '$name'"))

  /** reset all the timing numbers to 0 */
  def resetAll(): Unit =
    topLevelEntries.foreach(_.resetAll())

  /** Pretty print all the performance statistics. */
  def print(): Unit = {
    if (!enabled) return
    topLevelEntries.foreach(_.print("", logger))
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

  private def requireUniqueName(name: String): Unit =
    if (hmEntries.contains(name))
      throw new IllegalArgumentException(s"Profiler entry '$name' already exists")
}

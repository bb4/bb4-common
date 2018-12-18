/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

/**
  * Worker is an abstract class that you subclass to perform (usually gui related) work in a dedicated thread.
  * For instructions on and examples of using this class, see:
  * http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html
  *
  * You must invoke start() on the Worker after creating it.
  * I have modified the original Worker class so that it no longer
  * depends on Swing (hence the new name). I sometimes want to use this
  * class in a server process. So if you are using it on the gui make sure
  * that the body of the finished method is called from SwingUtilities.invokeLater().
  */
abstract class Worker() {

  /** value to return after asynchronous computation. See getValue(), setValue()   */
  private var returnValue: Any = _

  val thread = new Thread(doConstruct())
  thread.setName("Worker Thread") //NON-NLS

  /** worker thread under separate synchronization control. */
  final private val threadVar = new ThreadVar(thread)

  /** Start a thread that will call the construct method and then exit. */
  private def doConstruct(): Runnable = () => {
    try
      returnValue = construct
    finally threadVar.clear()
    // old: SwingUtilities.invokeLater(doFinished);
    // Now call directly, but if the body of finished is in the ui,
    // it should call SwingUtilities.invokeLater()
    finished()
  }

  def isWorking: Boolean = getValue == null

  /** @return the value produced by the worker thread, or null if it hasn't been constructed yet. */
  protected def getValue: Any = returnValue

  /**
    * Compute the value to be returned by the get method.
    * @return the result. Must not be null.
    */
  def construct: Any

  /** Start the worker thread. */
  def start(): Unit = {
    threadVar.start()
  }

  /** Called on the event dispatching thread (not on the worker thread)
    * after the construct method has returned.
    */
  def finished(): Unit = {
    // intentionally do nothing
  }

  /** Interrupts the worker thread.  Call this method
    * to force the worker to stop what it's doing.
    */
  def interrupt(): Unit = {
    threadVar.interrupt()
  }

  /** Return the value created by the construct method.
    * Returns null if either the constructing thread or the current
    * thread was interrupted before a value was produced.
    * @return the value created by the { @code construct} method
    */
  def get: Any = while (true) {
    val thread = threadVar.get
    if (thread == null) return getValue
    try thread.join()
    catch {
      case e: InterruptedException =>
        Thread.currentThread.interrupt() // propagate
        return null
    }
  }
}

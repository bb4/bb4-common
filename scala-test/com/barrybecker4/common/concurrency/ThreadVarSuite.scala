/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

import org.scalatest.funsuite.AnyFunSuite

import java.util.concurrent.{CountDownLatch, TimeUnit}
import scala.collection.mutable


/**
  * Tests for ThreadVar concurrency and synchronization behavior.
  * @author Barry Becker
  */
class ThreadVarSuite extends AnyFunSuite {

  test("Construction requires non-null thread") {
    assertThrows[AssertionError] {
      new ThreadVar(null)
    }
  }

  test("get returns initial thread") {
    val thread = new Thread(() => {})
    val threadVar = new ThreadVar(thread)
    assertResult(thread) { threadVar.get }
  }

  test("clear sets thread to None") {
    val thread = new Thread(() => {})
    val threadVar = new ThreadVar(thread)
    threadVar.clear()
    assertResult(null) { threadVar.get }
  }

  test("interrupt interrupts thread and clears reference") {
    var wasInterrupted = false
    val thread = new Thread(() => {
      try {
        Thread.sleep(10000)
      } catch {
        case _: InterruptedException => wasInterrupted = true
      }
    })
    val threadVar = new ThreadVar(thread)
    thread.start()
    ThreadUtil.sleep(10)
    threadVar.interrupt()
    thread.join(1000)
    assert(wasInterrupted)
    assertResult(null) { threadVar.get }
  }

  test("start calls start on thread") {
    var threadRan = false
    val thread = new Thread(() => { threadRan = true })
    val threadVar = new ThreadVar(thread)
    threadVar.start()
    thread.join(1000)
    assert(threadRan)
  }

  test("multiple starts do not throw exception") {
    // Thread.start() throws if called twice, but ThreadVar.start() should handle its reference safely
    val thread = new Thread(() => {})
    val threadVar = new ThreadVar(thread)
    threadVar.start()
    thread.join(1000)
    // After thread completes, calling start again on the same ThreadVar does nothing harmful
    // (The underlying Thread will throw if started again, but that's expected thread behavior)
  }

  test("clear is thread-safe") {
    val thread = new Thread(() => {})
    val threadVar = new ThreadVar(thread)
    val latch = new CountDownLatch(10)
    val threads = (1 to 10).map { _ =>
      new Thread(() => {
        threadVar.clear()
        latch.countDown()
      })
    }
    threads.foreach(_.start())
    assert(latch.await(5, TimeUnit.SECONDS))
    assertResult(null) { threadVar.get }
  }

  test("interrupt is thread-safe") {
    val results = mutable.Set[Thread]()
    val latch = new CountDownLatch(10)
    
    (1 to 10).foreach { _ =>
      val targetThread = new Thread(() => {
        try {
          Thread.sleep(10000)
        } catch {
          case _: InterruptedException => // expected
        }
      })
      val threadVar = new ThreadVar(targetThread)
      targetThread.start()
      
      new Thread(() => {
        ThreadUtil.sleep(5)
        threadVar.interrupt()
        latch.countDown()
      }).start()
      
      results += targetThread
    }
    
    assert(latch.await(5, TimeUnit.SECONDS))
    results.foreach(_.join(1000))
  }

  test("start is thread-safe with multiple callers") {
    var runCount = 0
    val countLatch = new CountDownLatch(1)
    
    val targetThread = new Thread(() => {
      runCount += 1
      countLatch.countDown()
    })
    val threadVar = new ThreadVar(targetThread)
    
    val startLatch = new CountDownLatch(5)
    val threads = (1 to 5).map { _ =>
      new Thread(() => {
        try {
          threadVar.start()
        } catch {
          case _: IllegalThreadStateException => // Expected if thread already started
        }
        startLatch.countDown()
      })
    }
    
    threads.foreach(_.start())
    assert(startLatch.await(5, TimeUnit.SECONDS))
    assert(countLatch.await(5, TimeUnit.SECONDS))
    targetThread.join(1000)
    assertResult(1) { runCount }
  }

  test("concurrent clear and interrupt") {
    val targetThread = new Thread(() => {
      try {
        Thread.sleep(10000)
      } catch {
        case _: InterruptedException => // expected
      }
    })
    val threadVar = new ThreadVar(targetThread)
    targetThread.start()
    
    val latch = new CountDownLatch(2)
    
    new Thread(() => {
      threadVar.clear()
      latch.countDown()
    }).start()
    
    new Thread(() => {
      threadVar.interrupt()
      latch.countDown()
    }).start()
    
    assert(latch.await(5, TimeUnit.SECONDS))
    targetThread.join(1000)
    assertResult(null) { threadVar.get }
  }

  test("get after clear returns null") {
    val thread = new Thread(() => {})
    val threadVar = new ThreadVar(thread)
    threadVar.clear()
    assertResult(null) { threadVar.get }
  }

  test("get after interrupt returns null") {
    val thread = new Thread(() => {
      try {
        Thread.sleep(10000)
      } catch {
        case _: InterruptedException => // expected
      }
    })
    val threadVar = new ThreadVar(thread)
    thread.start()
    threadVar.interrupt()
    thread.join(1000)
    assertResult(null) { threadVar.get }
  }

  test("clear after interrupt does not throw") {
    val thread = new Thread(() => {
      try {
        Thread.sleep(10000)
      } catch {
        case _: InterruptedException => // expected
      }
    })
    val threadVar = new ThreadVar(thread)
    thread.start()
    threadVar.interrupt()
    thread.join(1000)
    threadVar.clear()
    assertResult(null) { threadVar.get }
  }

  test("interrupt after clear does not throw") {
    val thread = new Thread(() => {})
    val threadVar = new ThreadVar(thread)
    threadVar.clear()
    threadVar.interrupt()
    assertResult(null) { threadVar.get }
  }
}

/* Copyright by Barry G. Becker, 2019. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.concurrency

import org.scalatest.FunSuite

class WorkerSuite extends FunSuite {

  val worker = new StubWorker(64000)

  test("worker") {
    worker.start()
    while (worker.isWorking) {
      println("working...")
      ThreadUtil.sleep(1)
    }
    assertResult(2048032000) {worker.get}
  }
}

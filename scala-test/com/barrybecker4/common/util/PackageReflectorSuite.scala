/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import org.scalatest.funsuite.AnyFunSuite


/**
  * @author Barry Becker
  */
class PackageReflectorSuite extends AnyFunSuite {
  private val reflector = new PackageReflector

  test("GetClasses") {
    val classes = reflector.getClasses("com.barrybecker4.common.testsupport")
    assertResult(2) { classes.size }
    assertResult("package$, package") {
      classes.map(_.getSimpleName).mkString(", ")
    }
  }

  test("GetClassesFromJar") {
    val classes = reflector.getClasses("org.junit.jupiter.api")
    assert(classes.nonEmpty, "JUnit Jupiter API should be resolved from a dependency jar")
    assert(classes.exists(_.getSimpleName == "Assertions"))
  }

  test("GetClassesWhenNone") {
    val classes = reflector.getClasses("com.invalid")
    assertResult(0) { classes.size }
  }
}

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

  /* not working */
  test("GetClassesFromJar") {
    val classes = reflector.getClasses("org.junit.runner")
    assertResult(12) { classes.size }
    assertResult("JUnitCommandLineParseResult") { classes.head.getSimpleName }
  }

  test("GetClassesWhenNone") {
    val classes = reflector.getClasses("com.invalid")
    assertResult(0) { classes.size }
  }
}

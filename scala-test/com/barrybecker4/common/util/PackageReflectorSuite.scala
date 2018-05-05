/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import org.scalatest.FunSuite


/**
  * @author Barry Becker
  */
class PackageReflectorSuite extends FunSuite {
  private var reflector = new PackageReflector

  test("GetClasses") {
    val classes = reflector.getClasses("com.barrybecker4.common.format")
    assertResult(9) { classes.size }
    assertResult("CurrencyFormatterSuite") { classes.head.getSimpleName }
  }

  /* not working
  test("GetClassesFromJar") {
    val classes = reflector.getClasses("org.junit.runner")
    assertResult(9) { classes.size }
    assertResult("Computer") { classes.head.getSimpleName }
  }*/

  test("GetClassesWhenNone") {
    val classes = reflector.getClasses("com.invalid")
    assertResult(0) { classes.size }
  }
}

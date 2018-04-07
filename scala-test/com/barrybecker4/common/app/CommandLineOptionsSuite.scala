/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.app

import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class CommandLineOptionsSuite extends FunSuite {

  test("CommandLineOptionsToString") {
    val testArgs = Array("-a", "b", "-c", "dog", "-e", "-f", "-type", "foo", "-h")
    val options = new CommandLineOptions(testArgs)
    assertResult("{a -> b, c -> dog, e -> null, f -> null, type -> foo, h -> null}") { options.toString }
  }

  test("EmptyCommandLineOptions") {
    val testArgs = Array[String]()
    val options = new CommandLineOptions(testArgs)
    assertResult("{}") { options.toString }
  }

  test("CommandLineOptionsWithNoDash") {
    assertThrows[AssertionError] {
      val testArgs = Array("a", "b", "-c", "dog")
      val options = new CommandLineOptions(testArgs)
    }
  }

  test("CommandLineOptionsWithSpace") {
    val testArgs = Array("-a", " b", "-c ", "dog")
    val options = new CommandLineOptions(testArgs)
    assertResult("{a -> b, c -> dog}") { options.toString }
  }

  test("CommandLineOptionsWithoutValues") {
    val testArgs = Array("-abc", "-c", "dog", "-efg")
    val options = new CommandLineOptions(testArgs)
    assertResult("{abc -> null, c -> dog, efg -> null}") { options.toString }
  }
}
/* Copyright by Barry G. Becker, 2000-2021. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.app

import org.scalatest.funsuite.AnyFunSuite


class ClassLoaderSingletonSuite extends AnyFunSuite {

  test("getClassLoader returns non-null") {
    val loader = ClassLoaderSingleton.getClassLoader
    assert(loader != null)
  }

  test("getClassLoader returns same instance on multiple calls") {
    val loader1 = ClassLoaderSingleton.getClassLoader
    val loader2 = ClassLoaderSingleton.getClassLoader
    assert(loader1 eq loader2)
  }

  test("getClassLoader returns context class loader") {
    val expectedLoader = Thread.currentThread.getContextClassLoader
    val loader = ClassLoaderSingleton.getClassLoader
    assertResult(expectedLoader) { loader }
  }

  test("loadClass loads existing class") {
    val clazz = ClassLoaderSingleton.loadClass("java.lang.String")
    assertResult(classOf[String]) { clazz }
  }

  test("loadClass loads class from package") {
    val clazz = ClassLoaderSingleton.loadClass("java.util.ArrayList")
    assertResult(classOf[java.util.ArrayList[?]]) { clazz }
  }

  test("loadClass throws IllegalArgumentException for non-existent class") {
    val exception = intercept[IllegalArgumentException] {
      ClassLoaderSingleton.loadClass("com.nonexistent.FakeClass")
    }
    assert(exception.getMessage.contains("Unable to find the class"))
    assert(exception.getMessage.contains("com.nonexistent.FakeClass"))
    assert(exception.getCause.isInstanceOf[ClassNotFoundException])
  }

  test("loadClass throws IllegalArgumentException for empty class name") {
    val exception = intercept[IllegalArgumentException] {
      ClassLoaderSingleton.loadClass("")
    }
    assert(exception.getCause.isInstanceOf[ClassNotFoundException])
  }

  test("loadClass throws IllegalArgumentException for invalid class name") {
    val exception = intercept[IllegalArgumentException] {
      ClassLoaderSingleton.loadClass("Not.A.Valid.ClassName!")
    }
    assert(exception.getMessage.contains("Unable to find the class"))
    assert(exception.getCause.isInstanceOf[ClassNotFoundException])
  }

  test("loadClass loads class from project") {
    val clazz = ClassLoaderSingleton.loadClass("com.barrybecker4.common.app.ClassLoaderSingleton")
    assert(clazz.getName == "com.barrybecker4.common.app.ClassLoaderSingleton")
  }

  test("loadClass can load interface") {
    val clazz = ClassLoaderSingleton.loadClass("java.util.List")
    assert(clazz.isInterface)
  }

  test("loadClass can load primitive wrapper") {
    val clazz = ClassLoaderSingleton.loadClass("java.lang.Integer")
    assertResult(classOf[Integer]) { clazz }
  }
}

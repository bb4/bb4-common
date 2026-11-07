/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.app


/** Allows getting a class loader from a static context.
  * It also avoids the same class being loaded multiple times by different loaders.
  */
object ClassLoaderSingleton {
  private lazy val loader: ClassLoader = Thread.currentThread.getContextClassLoader

  def getClassLoader: ClassLoader = loader

  /** @param className the class to load.
    * @return the loaded class.
    */
  def loadClass(className: String): Class[?] =
    try
      Class.forName(className)
    catch {
      case e: ClassNotFoundException =>
        throw new IllegalArgumentException(
          s"Unable to find the class $className. Verify that it is in the classpath.", e)
    }
}

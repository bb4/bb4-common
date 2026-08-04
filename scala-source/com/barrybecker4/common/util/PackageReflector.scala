/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT*/
package com.barrybecker4.common.util

import com.barrybecker4.common.app.ClassLoaderSingleton

import java.io.{File, IOException}
import java.net.{URI, URLDecoder}
import java.util.zip.ZipInputStream
import scala.collection.mutable.ArrayBuffer


/**
  * Use to find all classes in a specified package on the classpath.
  * It will find the classes whether they are classes in the project or jar file dependencies.
  * Could be useful in a plugin implementation.
  * @author Barry Becker
  */
object PackageReflector {
  private val CLASS_EXT = ".class"
}

/** Used to find classes in packages.
  * @author Barry Becker
  */
class PackageReflector() {

  /** Finds all classes in the specified package accessible from the class loader.
    * @param packageName the package to search in.
    * @return a list of classes found in the classpath
    */
  @throws[ClassNotFoundException]
  @throws[IOException]
  def getClasses(packageName: String): Seq[Class[?]] = {
    val files = getClassNames(packageName)
    getClassesFromNames(packageName, files)
  }

  @throws[IOException]
  private def getClassNames(packageName: String): IndexedSeq[String] = {
    val packagePath = packageName.replace('.', '/')
    val classLoader = ClassLoaderSingleton.getClassLoader
    val classNames = ArrayBuffer[String]()
    val resources = classLoader.getResources(packagePath)
    while (resources.hasMoreElements) {
      val resource = resources.nextElement
      val dirPath = URLDecoder.decode(resource.getFile, "UTF-8")
      if (dirPath.startsWith("file:") && dirPath.contains("!")) {
        classNames.appendAll(getClassNamesFromJar(dirPath, packageName))
      } else {
        val dir = new File(dirPath)
        val fileArr = dir.listFiles
        val names = getClassNamesFromFiles(
          if (fileArr == null) IndexedSeq.empty else fileArr.toIndexedSeq)
        classNames.appendAll(names)
      }
    }
    classNames.toIndexedSeq
  }

  @throws[IOException]
  private def getClassNamesFromJar(path: String, packageName: String): Set[String] = {
    val classNameSet = ArrayBuffer[String]()
    val split = path.split("!")
    val jar = URI.create(split(0)).toURL
    val zip = new ZipInputStream(jar.openStream)
    var entry = zip.getNextEntry
    while (entry != null) {
      if (entry.getName.endsWith(PackageReflector.CLASS_EXT)) {
        val className = entry.getName
          .replaceAll("[$].*", "")
          .replaceAll("[.]class", "").replace('/', '.')
        if (className.startsWith(packageName)) {
          val name = className.substring(packageName.length + 1)
          if (!name.contains("."))
            classNameSet.append(name)
        }
      }
      entry = zip.getNextEntry
    }
    classNameSet.toSet
  }

  private def getClassNamesFromFiles(files: IndexedSeq[File]): IndexedSeq[String] =
    files.collect {
      case file if file.getName.endsWith(PackageReflector.CLASS_EXT) =>
        file.getName.substring(0, file.getName.length - PackageReflector.CLASS_EXT.length)
    }

  @throws[ClassNotFoundException]
  private def getClassesFromNames(packageName: String, classNames: Seq[String]): Seq[Class[?]] = {
    val classes = ArrayBuffer[Class[?]]()
    for (className <- classNames) {
      classes.append(Class.forName(packageName + '.' + className))
    }
    classes.toList
  }
}

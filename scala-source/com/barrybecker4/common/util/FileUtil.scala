/*
 * Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT
 */
package com.barrybecker4.common.util

import java.io._
import java.net.URL
import java.security.AccessControlException

import com.barrybecker4.common.app.ClassLoaderSingleton

/**
  * Miscellaneous commonly used file related static utility methods.
  *
  * @author Barry Becker
  */
object FileUtil {
  /** Get the correct file separator whether on windows (\) or linux (/).
    * Getting error in applets if trying to use System.getProperty("file.separator")
    */
  val FILE_SEPARATOR = "/"

  /**
    * Try not to use this. If this is called from an applet, it will give a security exception.
    * @return home directory. Assumes running as an Application.
    */
  def getHomeDir: String = {
    var home = FILE_SEPARATOR
    try
      home = System.getProperty("user.dir") + FILE_SEPARATOR
    catch {
      case e: AccessControlException =>
        println("You do not have access to user.dir. This can happen when running as an applet. ", e)
    }
    home
  }

  /** Tries to create the specified directory if it does not exist.
    * Throws IOException if any problem creating the specified directory
    * @param path path to the directory to verify
    */
  @throws[IOException]
  def verifyDirectoryExistence(path: String): Unit = {
    val directory = new File(path)
    if (!directory.exists) {
      val success = directory.mkdir
      if (!success) throw new IOException("Could not create directory: " + directory.getAbsolutePath)
    }
  }

  /** Create a PrintWriter with utf8 encoding.
    * @param filename including the full path
    * @return new PrintWriter instance. Returns null if there was a problem creating it.
    */
  def createPrintWriter(filename: String): PrintWriter = {
    var outfile: PrintWriter = null
    try
      outfile = new PrintWriter(
        new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, false), "UTF-8"))
      )
    catch {
      case e: IOException => e.printStackTrace()
    }
    outfile
  }

  /** @return a URL given the path to a file */
  def getURL(sPath: String): URL = getURL(sPath,  failIfNotFound = true)

  /** @param sPath          the file path to get URL for
    * @param failIfNotFound throws IllegalArgumentException if not found in path
    * @return a URL given the path to an existing file.
    */
  def getURL(sPath: String, failIfNotFound: Boolean): URL = {
    val url = ClassLoaderSingleton.getClassLoader.getResource(sPath)
    if (url == null && failIfNotFound) throw new IllegalArgumentException("failed to create url for  " + sPath)
    url
  }

  /** @param sPath          the file path to get URL for
    * @param failIfNotFound throws IllegalArgumentException if not found in path
    * @return a stream given the path to an existing file.
    */
  def getResourceAsStream(sPath: String, failIfNotFound: Boolean): InputStream = {
    val stream = ClassLoaderSingleton.getClassLoader.getResourceAsStream(sPath)
    if (stream == null && failIfNotFound) throw new IllegalArgumentException("failed to find " + sPath)
    stream
  }

  /** Read a text file. Throws IllegalStateException if could not read the file
    * @param filename name of file to read from
    * @return text within the file
    */
  def readTextFile(filename: String): String = {
    var br: BufferedReader = null
    val bldr = new StringBuilder(1000)
    try {
      br = new BufferedReader(new FileReader(filename))
      var sCurrentLine: String = br.readLine()
      while (sCurrentLine != null) {
        bldr.append(sCurrentLine).append('\n')
        sCurrentLine = br.readLine()
      }
      bldr.toString
    } catch {
      case e: IOException => throw new IllegalStateException("Could not read " + filename, e)
    } finally {
      try if (br != null) br.close()
    }
  }

  /** Get all files in a directory (not recursive)
    * @param directory full path
    * @return the list of all files in the specified directory
    */
  def getFilesInDirectory(directory: String): Array[File] = {
    val dir = new File(directory)
    if (!dir.isDirectory) {
      throw new IllegalArgumentException(directory + " was not a directory")
    }
    val files = dir.listFiles
    if (files == null) {
      throw new IllegalStateException("Invalid: " + directory)
    }
    files.filter(_.isFile)
  }
}
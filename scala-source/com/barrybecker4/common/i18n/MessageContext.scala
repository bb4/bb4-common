/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.i18n

import com.barrybecker4.common.app.ILog

import java.text.MessageFormat
import java.util.{Locale, MissingResourceException, ResourceBundle}
import javax.swing.JComponent
import scala.collection.mutable.ArrayBuffer


object MessageContext {
  val DEFAULT_LOCALE: LocaleType = LocaleType.ENGLISH
}

/**
  * Manage access to localized message bundles.
  * When creating an instance specify the paths to the resource bundles to use.
  * @param resourcePaths list of paths to message bundles
  * @author Barry Becker
  */
class MessageContext(var resourcePaths: List[String]) {

  /** the list of bundles to look for messages in */
  private val messagesBundles = ArrayBuffer[ResourceBundle]()

  /** logger object. Use console by default. */
  private var logger: ILog = _

  /** debug level */
  private var debug = 0

  private var currentLocale = MessageContext.DEFAULT_LOCALE

  /** @param resourcePath path to message bundle*/
  def this(resourcePath: String) = {
    this(List(resourcePath))
  }

  /** @param resourcePath another resource path to get a message bundle from. */
  def addResourcePath(resourcePath: String): Unit = {
    if (!resourcePaths.contains(resourcePath)) {
      resourcePaths :+= resourcePath
      messagesBundles.clear()
    }
  }

  def setDebugMode(debugMode: Int): Unit = {
    debug = debugMode
  }

  /** @param logger the logging device. Determines where the output goes. */
  def setLogger(logger: ILog): Unit = {
    assert(logger != null)
    this.logger = logger
  }

  private def log(logLevel: Int, message: String): Unit = {
    if (logger == null) throw new RuntimeException("Set a logger on the MessageContext before calling log.")
    logger.print(logLevel, debug, message)
  }

  /** Set or change the current locale.
    * @param localeName name locale to use (something like ENGLISH, GERMAN, etc)
    */
  def setLocale(localeName: String): Unit = {
    setLocale(getLocale(localeName, finf = true))
  }

  /** Set or change the current locale.
    * @param locale locale to use
    */
  def setLocale(locale: LocaleType): Unit = {
    currentLocale = locale
    messagesBundles.clear()
    initMessageBundles(currentLocale)
    JComponent.setDefaultLocale(currentLocale.locale)
  }

  def getLocale: Locale = currentLocale.locale

  /** Look first in the common message bundle.
    * If not found there, look in the application specific bundle if there is one.
    * @param key the message key to find in resource bundle.
    * @return the localized message label
    */
  def getLabel(key: String): String = getLabel(key, null)

  /** Look first in the common message bundle.
    * If not found there, look in the application specific bundle if there is one.
    * @param key    the message key to find in resource bundle.
    * @param params typically a list of strings to use as parameters to the template defined by the message from key.
    * @return the localized message label
    */
  def getLabel(key: String, params: Array[AnyRef]): String =
    findLabelTemplate(key) match {
      case Some(template) => formatLabel(template, params)
      case None =>
        val msg = "Could not find label for " + key + " among " + resourcePaths.toString // NON-NLS
        log(0, msg)
        throw new MissingResourceException(msg, resourcePaths.toString, key)
    }

  private def findLabelTemplate(key: String): Option[String] = {
    if (messagesBundles.isEmpty) initMessageBundles(currentLocale)
    var i = 0
    while (i < messagesBundles.size) {
      val bundle = messagesBundles(i)
      if (bundle.containsKey(key)) {
        return Some(bundle.getString(key))
      }
      i += 1
    }
    None
  }

  private def formatLabel(label: String, params: Array[AnyRef]): String =
    if (params == null) label
    else {
      val formatter = new MessageFormat(label, currentLocale.locale)
      formatter.format(params)
    }

  private def initMessageBundles(locale: LocaleType): Unit = {
    for (path <- resourcePaths) {
      val bundle = ResourceBundle.getBundle(path, locale.locale)
      if (bundle == null) throw new IllegalArgumentException("Messages bundle for " + path + " was not found.")
      messagesBundles.append(bundle)
    }
    JComponent.setDefaultLocale(locale.locale)
  }

  /** Looks up a LocaleType for a given locale name (enum constant name, e.g. ENGLISH).
    * @param finf if true, throw when the name is unknown; if false, return [[LocaleType.ENGLISH]] after logging.
    * @return locale for the name
    */
  def getLocale(name: String, finf: Boolean): LocaleType = {
    LocaleType.fromString(name) match {
      case Some(t) => t
      case None =>
        logInvalidLocaleName(name, finf)
        if (finf) {
          throw new IllegalArgumentException(
            "Unknown locale '" + name + "'. Use one of: " + LocaleType.values.mkString(", "))
        }
        LocaleType.ENGLISH
    }
  }

  private def logInvalidLocaleName(name: String, finf: Boolean): Unit = {
    if (logger == null) return
    log(0, "***************")
    log(0, name + " is not a valid locale. We currently only support: ") // NON-NLS
    for (v <- LocaleType.values) {
      log(0, v.toString)
    }
    if (!finf) log(0, "Defaulting to English.")
    log(0, "***************")
  }
}

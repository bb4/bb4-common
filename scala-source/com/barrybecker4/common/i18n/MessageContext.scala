/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.i18n

import java.text.MessageFormat
import java.util.{Locale, MissingResourceException, ResourceBundle}
import com.barrybecker4.common.app.ILog
import javax.swing.JComponent
import scala.collection.mutable.ArrayBuffer


object MessageContext {
  val DEFAULT_LOCALE: LocaleType = ENGLISH
}

/**
  * Manage access to localized message bundles.
  * When creating an instance specify the paths to the resource bundles to use.
  * @param resourcePaths list of paths to message bundles
  * @author Barry Becker
  */
class MessageContext(var resourcePaths: List[String]) {

  /** the list of bundles to look for messages in */
  private var messagesBundles = ArrayBuffer[ResourceBundle]()

  /** logger object. Use console by default. */
  private var logger: ILog = _

  /** debug level */
  private var debug = 0

  private var currentLocale = MessageContext.DEFAULT_LOCALE

  /** @param resourcePath path to message bundle*/
  def this(resourcePath: String) {
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
    setLocale(getLocale(localeName, true))
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
    * @param params typically a list of string sto use a parameters to the template defined by the message from key.
    * @return the localized message label
    */
  def getLabel(key: String, params: Array[AnyRef]): String = {
    var label = key
    if (messagesBundles.isEmpty) initMessageBundles(currentLocale)
    var found = false
    val numBundles = messagesBundles.size
    var ct = 0
    while ( {
      !found && ct < numBundles
    }) {
      val bundle = messagesBundles(ct)
      ct += 1
      if (bundle.containsKey(key)) {
        label = bundle.getString(key)
        if (params != null) {
          val formatter = new MessageFormat(label, currentLocale.locale)
          label = formatter.format(params)
        }
        found = true
      }
    }
    if (!found) {
      val msg = "Could not find label for " + key + " among " + resourcePaths.toString // NON-NLS
      log(0, msg)
      throw new MissingResourceException(msg, resourcePaths.toString, key)
    }
    label
  }

  private def initMessageBundles(locale: LocaleType): Unit = {
    //import scala.collection.JavaConversions._
    for (path <- resourcePaths) {
      val bundle = ResourceBundle.getBundle(path, locale.locale)
      if (bundle == null) throw new IllegalArgumentException("Messages bundle for " + path + " was not found.")
      messagesBundles.append(bundle)
    }
    JComponent.setDefaultLocale(locale.locale)
  }

  /** Looks up a LocaleType for a given locale name.
    * @param finf fail if not found.
    * @return locale the name of a local. Something like ENGLISH, GERMAN, etc
    * @throws Error if the name is not a member of the enumeration
    */
  def getLocale(name: String, finf: Boolean): LocaleType = {
    var theType: LocaleType = ENGLISH // english is the default
    try
      theType = LocaleType.valueOf(name)
    catch {
      case e: IllegalAccessError =>
        log(0, "***************")
        log(0, name + " is not a valid locale. We currently only support: ") // NON-NLS

        val values = LocaleType.VALUES
        for (newVar <- values) {
          log(0, newVar.toString)
        }
        log(0, "Defaulting to English.")
        log(0, "***************")
        assert(!finf)
        throw e
    }
    theType
  }
}
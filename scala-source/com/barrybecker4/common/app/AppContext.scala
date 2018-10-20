package com.barrybecker4.common.app

import java.text.NumberFormat
import com.barrybecker4.common.i18n.MessageContext

/**
  * Manage application context such as logging, debugging, resources.
  * @author Barry Becker
  */
object AppContext {
  /** logger object. */
  private var logger: ILog = _
  /** if greater than 0, then debug mode is on. the higher the number, the more info that is printed.  */
  private val DEBUG: Int = 0
  /** now the variable forms of the above defaults */
  private var debug: Int = DEBUG
  private var messageContext: MessageContext = _

  /** Initialize the app context once a the start of a program
    * @param localeName    name of the locale to use (ENGLISH, GERMAN, etc)
    * @param resourcePaths locations of the properties file in the classpath pointing to message bundles
    * @param logger        logging implementation
    */
  def initialize(localeName: String, resourcePaths: List[String], logger: ILog): Unit = {
    assert(resourcePaths != null)
    assert(logger != null)
    AppContext.logger = logger
    messageContext = new MessageContext(resourcePaths)
    messageContext.setLogger(AppContext.logger)
    messageContext.setDebugMode(debug)
    messageContext.setLocale(localeName)
  }

  /** Allow setting a custom message context for testing purposes */
  def injectMessageContext(context: MessageContext): Unit = {
    messageContext = context
  }

  def isInitialized: Boolean = logger != null

  /** @return the level of debugging in effect */
  def getDebugMode: Int = debug

  /** @param debug the debug level to use. If 0, then all logging performed.*/
  def setDebugMode(debug: Int): Unit = {
    AppContext.debug = debug
  }

  /** Log a message using the internal logger object */
  def log(logLevel: Int, message: String): Unit = {
    assert(logger != null, "Must set a logger before logging")
    logger.print(logLevel, getDebugMode, message)
  }

  def getCurrencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(messageContext.getLocale)

  /** @param key message key
    * @return the localized message label
    */
  def getLabel(key: String): String = {
    if (messageContext != null)
      messageContext.getLabel(key)
    else {
      println("Could not get label for " + key + " because the messageContext was null.")
      key
    }
  }

  /** Use this version if there are parameters to the localized string
    * @param key message key
    * @return the localized message label
    */
  def getLabel(key: String, params: Array[AnyRef]): String = messageContext.getLabel(key, params)

  def main(args: Array[String]): Unit = {
    println("The bb4-common project is meant to be used as a library.")
    println("The AppContext is used to manage global application resources like i18 and logging.")
  }
}
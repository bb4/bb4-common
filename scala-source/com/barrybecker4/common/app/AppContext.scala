package com.barrybecker4.common.app

import com.barrybecker4.common.i18n.MessageContext

import java.text.NumberFormat

/**
  * Manage application context such as logging, debugging, resources.
  * @author Barry Becker
  */
object AppContext {
  /** logger object. */
  private var logger: Option[ILog] = None

  /** if greater than 0, then debug mode is on. the higher the number, the more info that is printed.  */
  private val DEBUG: Int = 0

  /** now the variable forms of the above defaults */
  private var debug: Int = DEBUG

  private var messageContext: Option[MessageContext] = None

  def isInitialized: Boolean = logger.isDefined

  /** Initialize the app context once a the start of a program
    * @param localeName    name of the locale to use (ENGLISH, GERMAN, etc)
    * @param resourcePaths locations of the properties file in the classpath pointing to message bundles
    * @param logger        logging implementation
    */
  def initialize(localeName: String, resourcePaths: List[String], logger: ILog): Unit = {
    assert(resourcePaths != null)
    assert(logger != null)
    AppContext.logger = Some(logger)
    val context = new MessageContext(resourcePaths)
    messageContext = Some(context)
    context.setLogger(logger)
    context.setDebugMode(debug)
    context.setLocale(localeName)
  }

  /** Allow setting a custom message context for testing purposes */
  def injectMessageContext(context: MessageContext): Unit = {
    messageContext = Option(context)
  }

  /** @return the level of debugging in effect */
  def getDebugMode: Int = debug

  /** @param debug the debug level to use. If 0, then all logging performed.*/
  def setDebugMode(debug: Int): Unit = {
    AppContext.debug = debug
  }

  /** Log a message using the internal logger object */
  def log(logLevel: Int, message: String): Unit = {
    assert(logger.isDefined, "Must set a logger before logging")
    logger.get.print(logLevel, getDebugMode, message)
  }

  def getCurrencyFormat: NumberFormat =
    NumberFormat.getCurrencyInstance(messageContext.get.getLocale)

  /** @param key message key
    * @return the localized message label
    */
  def getLabel(key: String): String =
    messageContext match {
      case Some(ctx) => ctx.getLabel(key)
      case None =>
        println("Could not get label for " + key + " because the messageContext was null.")
        key
    }

  /** Use this version if there are parameters to the localized string
    * @param key message key
    * @return the localized message label
    */
  def getLabel(key: String, params: Array[AnyRef]): String =
    messageContext match {
      case Some(ctx) => ctx.getLabel(key, params)
      case None =>
        println("Could not get label for " + key + " because the messageContext was null.")
        key
    }

  def main(args: Array[String]): Unit = {
    println("The bb4-common project is meant to be used as a library.")
    println("The AppContext is used to manage global application resources like i18 and logging.")
  }
}

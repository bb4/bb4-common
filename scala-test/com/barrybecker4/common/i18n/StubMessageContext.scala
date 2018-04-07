/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.i18n

import com.barrybecker4.common.i18n.MessageContext

/**
  * Used for testing to provide a fake message context.
  * This should probably go in a separate testsupport package, but that does not exist yet.
  * @author Barry Becker
  */
final class StubMessageContext() extends MessageContext("") {

  /** Look first in the common message bundle.
    * If not found there, look in the application specific bundle if there is one.
    * @param key the message key to find in resource bundle.
    * @return the localized message label
    */
  override def getLabel(key: String): String = key.toLowerCase

  /** Look first in the common message bundle.
    * If not found there, look in the application specific bundle if there is one.
    * @param key    the message key to find in resource bundle.
    * @param params typically a list of string sto use a parameters to the template defined by the message from key.
    * @return the localized message label
    */
  override def getLabel(key: String, params: Array[AnyRef]): String = key.toLowerCase
}
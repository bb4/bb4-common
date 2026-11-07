/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import org.w3c.dom.{Document, Node}

import scala.collection.mutable.ListBuffer

/** Document transformations such as use-element substitution and whitespace cleanup. */
object DomTransformer {
  private val USE_ELEMENT = "use"

  private def isWhitespaceOnlyTextNode(n: Node): Boolean = {
    val name = n.getNodeName
    name != null && name.startsWith("#text") && {
      val text = n.getNodeValue
      text != null && text.matches("[ \\t\n\\x0B\\f\\r]*")
    }
  }

  /** Go through the dom hierarchy and remove spurious text nodes and also
    * replace "use" nodes with a deep copy of what they refer to.
    */
  private[xml] def postProcessDocument(root: Node, document: Document, replaceUseWithDeepCopy: Boolean): Unit = {
    val l = root.getChildNodes
    val deleteList = ListBuffer[Node]()
    var i = 0
    while (i < l.getLength) {
      val n = l.item(i)
      val name = n.getNodeName
      if (isWhitespaceOnlyTextNode(n))
        deleteList.append(n)
      postProcessDocument(n, document, replaceUseWithDeepCopy)
      if (name != null && USE_ELEMENT == name)
        substituteUseElement(root, n, document, replaceUseWithDeepCopy)
      i += 1
    }
    deleteList.foreach(c => root.removeChild(c))
  }

  private def substituteUseElement(
      root: Node,
      n: Node,
      document: Document,
      replaceUseWithDeepCopy: Boolean): Unit = {
    val attrs = n.getAttributes
    val attr = attrs.item(0)
    assert("ref" == attr.getNodeName, "attr name=" + attr.getNodeName)
    val attrValue = attr.getNodeValue
    val element = DomQuery.findElementById(document, attrValue).getOrElse(
      throw new IllegalStateException(
        "No element with id '" + attrValue + "' for <use> reference"))
    val clonedElement = element.cloneNode(replaceUseWithDeepCopy)
    postProcessDocument(clonedElement, document, replaceUseWithDeepCopy)
    root.replaceChild(clonedElement, n)
  }
}

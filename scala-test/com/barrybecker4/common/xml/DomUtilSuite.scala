/* Copyright by Barry G. Becker, 2019. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import com.barrybecker4.common.util.FileUtil
import org.scalatest.FunSuite
import org.w3c.dom.{Document, Node}


class DomUtilSuite extends FunSuite {

  private val PREFIX = "com/barrybecker4/common/xml/"

  test("read XML file with embedded dtd") {
    val url = FileUtil.getURL(PREFIX + "p_embedded_dtd.xml")
    val xmlDocument = DomUtil.parseXML(url)

    println("doc = " + xmlDocument)
    assertResult("plugins") { xmlDocument.getDocumentElement.getTagName }

    val result = DomUtil.toString(xmlDocument.getDocumentElement.asInstanceOf[Node], 1)
    assert(result.startsWith("    Node: <plugins>  author=\"Barry Becker\"  date=\"10/19/2004\""))
  }

  test("read XML file with remote dtd") {

    val url = FileUtil.getURL(PREFIX + "p_remote_dtd.xml")
    val xmlDocument = DomUtil.parseXML(url)

    println("doc = " + xmlDocument)
    assertResult("plugins") { xmlDocument.getDocumentElement.getTagName }

    val result = DomUtil.toString(xmlDocument.getDocumentElement.asInstanceOf[Node], 1)
    assert(result.startsWith("    Node: <plugins>  author=\"Barry Becker\"  date=\"10/19/2004\""))
  }

  test("read XML web-app file with remote dtd") {

    val url = FileUtil.getURL(PREFIX + "web-app.xml")
    val xmlDocument = DomUtil.parseXML(url)

    println("doc = " + xmlDocument)
    assertResult("web-app") { xmlDocument.getDocumentElement.getTagName }

    val result = DomUtil.toString(xmlDocument.getDocumentElement.asInstanceOf[Node], 1)
    assert(result.startsWith("    Node: <web-app>"))
  }
}

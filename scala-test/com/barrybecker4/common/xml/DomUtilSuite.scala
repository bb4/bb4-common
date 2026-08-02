/* Copyright by Barry G. Becker, 2019. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.xml

import com.barrybecker4.common.util.FileUtil
import org.scalatest.funsuite.AnyFunSuite
import org.w3c.dom.{Document, Node}

import java.io.File
import java.nio.file.Files


class DomUtilSuite extends AnyFunSuite {

  private val PREFIX = "com/barrybecker4/common/xml/"

  test("read XML file with embedded dtd") {
    val url = FileUtil.getURL(PREFIX + "p_embedded_dtd.xml")
    val xmlDocument = DomUtil.parseXML(url)

    println("doc = " + xmlDocument)
    assertResult("plugins") { xmlDocument.getDocumentElement.getTagName }

    val result = DomUtil.asString(xmlDocument.getDocumentElement.asInstanceOf[Node], 1)
    assert(result.startsWith("    Node: <plugins>  author=\"Barry Becker\"  date=\"10/19/2004\""))
  }

  test("read XML file with remote dtd") {

    val url = FileUtil.getURL(PREFIX + "p_remote_dtd.xml")
    val xmlDocument = DomUtil.parseXML(url)

    println("doc = " + xmlDocument)
    assertResult("plugins") { xmlDocument.getDocumentElement.getTagName }

    val result = DomUtil.asString(xmlDocument.getDocumentElement.asInstanceOf[Node], 1)
    assert(result.startsWith("    Node: <plugins>  author=\"Barry Becker\"  date=\"10/19/2004\""))
  }

  test("read XML web-app file with remote dtd") {

    val url = FileUtil.getURL(PREFIX + "web-app.xml")
    val xmlDocument = DomUtil.parseXML(url)

    println("doc = " + xmlDocument)
    assertResult("web-app") { xmlDocument.getDocumentElement.getTagName }

    val result = DomUtil.asString(xmlDocument.getDocumentElement.asInstanceOf[Node], 1)

    assert(result.startsWith("    Node: <web-app>"))
  }

  test("parse malformed XML file throws IllegalStateException") {
    val f = File.createTempFile("bad", ".xml")
    try {
      Files.writeString(f.toPath, "<not>")
      assertThrows[IllegalStateException](DomUtil.parseXMLFile(f))
    } finally f.delete()
  }

  test("substitute use refs without external DTD ID typing") {
    val f = File.createTempFile("use-refs", ".xml")
    try {
      Files.writeString(f.toPath,
        """<?xml version="1.0"?>
          |<!DOCTYPE hierarchy SYSTEM "https://example.invalid/missing.dtd">
          |<hierarchy>
          |  <node id="leaf" label="leaf"/>
          |  <node id="parent" label="parent">
          |    <use ref="leaf"/>
          |  </node>
          |</hierarchy>
          |""".stripMargin)
      val doc = DomUtil.parseXMLFile(f)
      val asText = DomUtil.asString(doc.getDocumentElement.asInstanceOf[Node], 0)
      assert(asText.contains("id=\"leaf\""), asText)
      assert(!asText.contains("<use>"), "use elements should be substituted away: " + asText)
      // parent should now have a cloned leaf child (two leaf id occurrences)
      assert(asText.split("id=\"leaf\"").length - 1 == 2, asText)
    } finally f.delete()
  }
}

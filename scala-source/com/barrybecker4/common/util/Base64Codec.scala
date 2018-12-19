/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import java.io._
import java.util.zip.{Inflater, InflaterInputStream}
import java.util.Base64
import java.util.zip.{Deflater, DeflaterOutputStream}

/**
  * Utility methods for Base64 compression and decompression.
  * @author Barry Becker
  */
object Base64Codec {
  val CONVERTER_UTF8 = "UTF8"

  /** Take a String and compress it.
    * See @decompress for reversing the compression.
    * @param data a string to compress.
    * @return compressed string representation.
    */
  def compress(data: String): String = {
    val byteOut = new ByteArrayOutputStream(512)
    val deflater = new Deflater
    val oStream = new DeflaterOutputStream(byteOut, deflater)
    try {
      oStream.write(data.getBytes(CONVERTER_UTF8))
      oStream.flush()
      oStream.close()
    } catch {
      case e: UnsupportedEncodingException =>
        throw new IllegalArgumentException("Unsupported encoding exception :" + e.getMessage, e)
      case e: IOException =>
        throw new IllegalStateException("io error :" + e.getMessage, e)
    }
    new String(Base64.getEncoder.encode(byteOut.toByteArray))
  }

  /** Take a String and decompress it.
    * @param data the compressed string to decompress.
    * @return the decompressed string.
    */
  def decompress(data: String): String = { // convert from string to bytes for decompressing
    val compressedDat = Base64.getDecoder.decode(data.getBytes)
    val in = new ByteArrayInputStream(compressedDat)
    val inflater = new Inflater
    val iStream = new InflaterInputStream(in, inflater)
    val cBuffer = new Array[Char](4096)
    val sBuf = new StringBuilder
    try {
      val iReader = new InputStreamReader(iStream, CONVERTER_UTF8)
      var done = false
      while (!done) {
        val numRead = iReader.read(cBuffer)
        println("numRead= " + numRead + " cbuf= " + cBuffer + " s=" + new String(cBuffer))
        if (numRead == -1) {
          done = true
        } else sBuf.append(new String(cBuffer, 0, numRead))
      }
    } catch {
      case e: UnsupportedEncodingException =>
        throw new IllegalArgumentException("Unsupported encoding exception :" + e.getMessage, e)
      case e: IOException =>
        throw new IllegalStateException("io error :" + e.getMessage, e)
    }
    sBuf.toString
  }
}

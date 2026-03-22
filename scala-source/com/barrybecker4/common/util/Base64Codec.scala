/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import java.io.*
import java.util.Base64
import java.util.zip.{Deflater, DeflaterOutputStream, Inflater, InflaterInputStream}

import scala.util.Using

/**
  * Utility methods for Base64 compression and decompression.
  * @author Barry Becker
  */
object Base64Codec {
  private val CONVERTER_UTF8 = "UTF8"

  /** Take a String and compress it.
    * See @decompress for reversing the compression.
    * @param data a string to compress.
    * @return compressed string representation.
    */
  def compress(data: String): String = {
    val byteOut = new ByteArrayOutputStream(512)
    val deflater = new Deflater
    try {
      Using.resource(new DeflaterOutputStream(byteOut, deflater)) { oStream =>
        oStream.write(data.getBytes(CONVERTER_UTF8))
        oStream.flush()
      }
      new String(Base64.getEncoder.encode(byteOut.toByteArray))
    } catch {
      case e: UnsupportedEncodingException =>
        throw new IllegalArgumentException("Unsupported encoding exception :" + e.getMessage, e)
      case e: IOException =>
        throw new IllegalStateException("io error :" + e.getMessage, e)
    }
  }

  /** Take a String and decompress it.
    * @param data the compressed string to decompress.
    * @return the decompressed string.
    */
  def decompress(data: String): String = {
    val compressedDat = Base64.getDecoder.decode(data.getBytes)
    val inflater = new Inflater
    try {
      Using.resource(new ByteArrayInputStream(compressedDat)) { bin =>
        Using.resource(new InflaterInputStream(bin, inflater)) { iStream =>
          Using.resource(new InputStreamReader(iStream, CONVERTER_UTF8)) { iReader =>
            val cBuffer = new Array[Char](4096)
            val sBuf = new StringBuilder
            var n = iReader.read(cBuffer)
            while (n != -1) {
              sBuf.append(new String(cBuffer, 0, n))
              n = iReader.read(cBuffer)
            }
            sBuf.toString
          }
        }
      }
    } catch {
      case e: UnsupportedEncodingException =>
        throw new IllegalArgumentException("Unsupported encoding exception :" + e.getMessage, e)
      case e: IOException =>
        throw new IllegalStateException("io error :" + e.getMessage, e)
    }
  }
}

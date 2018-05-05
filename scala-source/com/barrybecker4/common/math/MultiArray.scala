/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import java.util


/**
  * Provide support for high dimensional arrays of doubles.
  * Eventually this class should support multi-dimensional arrays of any type, but  for now it only supports doubles.
  * Use this class when you need to create an arbitrarily sized array of &gt; 1 dimension.
  * @author Barry Becker
  */
class MultiArray(val dims: Array[Int]) {

  /** handles converting from a raw index into an array of indices and back. */
  final private val indexer: MultiDimensionalIndexer = new MultiDimensionalIndexer(dims)
  val numValues: Long = indexer.getNumValues
  if (numValues > Integer.MAX_VALUE)
    throw new IllegalArgumentException("The array with dimensions " + util.Arrays.toString(dims) +
      " cannot have more values than " + Integer.MAX_VALUE)

  /** this will hold all the data for this array class. */
  private val arrayData: Array[Double] = new Array[Double](numValues.toInt)

  def getNumValues: Int = indexer.getNumValues.toInt

  /** @param index an integer index array of size numDims specifying a location in the data array.
    * @return the corresponding array value.
    */
  def get(index: Array[Int]): Double = arrayData(indexer.getRawIndex(index))

  /** @param index location in the data array.
    * @param value to assign to that location.
    */
  def set(index: Array[Int], value: Double): Unit =
    arrayData(indexer.getRawIndex(index)) = value

  /** see getIndexFromRaw for how we can get a multi-dim index from a single raw integer index.
    * @param rawIndex int specifying location in the multi dim array.
    */
  def getRaw(rawIndex: Int): Double = arrayData(rawIndex)
}

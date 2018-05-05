/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import java.util


/**
  * Provide support for indexing into high dimensional arrays.
  * It does not actually allocate memory for the large dimensional array. That is up to the client app to manage.
  * Can be used to create an arbitrarily sized array of &gt; 1 dimension.
  *
  * @author Barry Becker
  */
class MultiDimensionalIndexer(val dimensions: Array[Int]) {

  /** the size of each dimension in the multi-dim array. */
  final private val dims: Array[Int] = dimensions.clone
  /** the number of dimensions. May be any number  >= 0. */
  final private val numDims: Int = this.dims.length

  /** product of number of dimensions. All dimensions multiplied together. */
  final private val numVals: Long = getDimensionProduct
  if (numDims <= 0)
    throw new IllegalArgumentException("You must have > 0 dimension to use this class")
  if (numVals <= 0)
    throw new IllegalArgumentException("The product of the dimension lengths must be greater than 0. It was " + numVals)

  def getNumDims: Int = numDims

  /** @return the total number of values in the hypothetical array*/
  def getNumValues: Long = numVals

  /** @return the product of the first n dimensions. returns 1 if n is 0 */
  private def getDimensionProduct: Int = dims.product

  /** @param index n dimensional array of indices.
    * @return raw integer index from a integer array of indices.
    */
  def getRawIndex(index: Array[Int]): Int = {
    assert(index.length == numDims,
      "the index you specified has " + index.length + " values, when you need to specify " + numDims)
    var rawIndex: Int = index(numDims - 1)
    var offset: Int = dims(numDims - 1)
    for (i <- numDims - 2 to 0 by -1) {
      rawIndex += offset * index(i)
      offset *= dims(i)
    }
    rawIndex
  }

  /** @param rawIndex the raw integer index to convert to an array of int indices.
    * @return an integer array of indices from a raw integer index.
    */
  def getIndexFromRaw(rawIndex: Int): Array[Int] = {
    val index: Array[Int] = new Array[Int](numDims)
    var prod: Int = 1
    for (i <- numDims - 1 to 0 by -1) {
      index(i) = rawIndex / prod % dims(i)
      if (i > 0) prod *= dims(i)
    }
    index
  }

  /** @param rawIndex raw index
    * @return a string representation of the tuple. e.g. "3,5,9,3"
    */
  def getIndexKey(rawIndex: Int): String = {
    val index: Array[Int] = getIndexFromRaw(rawIndex)
    util.Arrays.toString(index)
  }
}
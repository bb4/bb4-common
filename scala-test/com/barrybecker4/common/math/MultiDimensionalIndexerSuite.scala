/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.math

import org.scalatest.FunSuite

/**
  * @author Barry Becker
  */
class MultiDimensionalIndexerSuite extends FunSuite {
  
  /** instance under test */
  private var indexer: MultiDimensionalIndexer = _

  /** must be at least one dim */
  test("CreateOLength0DArray(") {
    val dims = new Array[Int](0)
    assertThrows[IllegalArgumentException] {
      new MultiDimensionalIndexer(dims)
    }
  }

  /** dim lengths must be greater than 0 */
  test("Create0Length1DArray") {
    val dims = new Array[Int](1)
    assertThrows[IllegalArgumentException] {
      new MultiDimensionalIndexer(dims)
    }
  }

  test("Create1Length1DArray") {
    val dims = Array[Int](1)
    indexer = new MultiDimensionalIndexer(dims)
    assertResult(1) { indexer.getNumDims }
    assertResult(1) { indexer.getNumValues }
    assertIndicesEqual(Array[Int](0), indexer.getIndexFromRaw(0))
    assertResult("[0]") {indexer.getIndexKey(0)}
    //assertResult("Unexpected index key (indexer)", "0", indexer.getIndexKey(new int[] {0}));
  }

  test("Create2Length2DArray") {
    val dims = Array[Int](2, 2)
    indexer = new MultiDimensionalIndexer(dims)
    assertResult(2) { indexer.getNumDims }
    assertResult(4) { indexer.getNumValues }
    assertIndicesEqual(Array[Int](0, 0), indexer.getIndexFromRaw(0))
    assertIndicesEqual(Array[Int](1, 0), indexer.getIndexFromRaw(2))
    assertIndicesEqual(Array[Int](1, 1), indexer.getIndexFromRaw(3))
    assertResult( "[0, 0]") { indexer.getIndexKey(0)}
    assertResult("[0, 1]") { indexer.getIndexKey(1) }
    assertResult("[1, 1]") { indexer.getIndexKey(3) }
    //assertResult("Unexpected index key (indexer)", "0,1", indexer.getIndexKey(new int[] {0, 1}));
  }

  test("Create3DArray") {
    val dims = Array[Int](2, 3, 4)
    indexer = new MultiDimensionalIndexer(dims)
    assertResult(3) { indexer.getNumDims }
    assertResult(24) { indexer.getNumValues }
    assertIndicesEqual(Array[Int](0, 0, 0), indexer.getIndexFromRaw(0))
    assertIndicesEqual(Array[Int](0, 0, 2), indexer.getIndexFromRaw(2))
    assertIndicesEqual(Array[Int](0, 1, 0), indexer.getIndexFromRaw(4))
    assertIndicesEqual(Array[Int](1, 1, 3), indexer.getIndexFromRaw(19))
    assertResult("[0, 0, 0]") { indexer.getIndexKey(0)}
    assertResult("[1, 1, 3]") { indexer.getIndexKey(19) }
    assertResult("[1, 2, 0]") { indexer.getIndexKey(20) }
    assertResult("[1, 2, 1]") { indexer.getIndexKey(21) }
    assertResult("[1, 2, 2]") { indexer.getIndexKey(22) }
    assertResult("[1, 2, 3]") { indexer.getIndexKey(23) }
    //assertResult("Unexpected index key (indexer)", "0,1,3", indexer.getIndexKey(new int[] {0, 1, 3}));
  }

  private def assertIndicesEqual(expIndices: Array[Int], indices: Array[Int]): Unit = {
    assert(expIndices === indices)
  }
}

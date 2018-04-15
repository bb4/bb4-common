/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.util

import java.io.InputStream
import java.util.Scanner


object UnionFind {
  def create(in: InputStream): UnionFind = {
    val stdIn = new Scanner(in)
    val n = stdIn.nextInt
    val uf = new UnionFind(n)
    while ( {
      stdIn.hasNext
    }) {
      val p = stdIn.nextInt
      val q = stdIn.nextInt
      if (!uf.connected(p, q)) uf.union(p, q)
    }
    uf
  }

  /** Reads in a sequence of pairs of integers (between 0 and N-1) from standard in. Each integer represents an object.
    * If the objects are in different components, merge the two components and print the pair to standard output.
    */
  def main(args: Array[String]): Unit = {
    val uf = UnionFind.create(System.in)
    println(uf.getCount + " components")
  }
}

/**
  * The <tt>WeightedQuickUnionUF</tt> class represents a union-find data structure.
  * It supports the <em>union</em> and <em>find</em> operations, along with
  * methods for determining whether two objects are in the same component and the total number of components.
  * This implementation uses weighted quick union by size (without path compression).
  * Initializing a data structure with <em>N</em> objects takes linear time.
  * Afterwards, <em>union</em>, <em>find</em>, and <em>connected</em> take
  * logarithmic time (in the worst case) and <em>count</em> takes constant time.
  *
  * For additional documentation, see <a href="http://algs4.cs.princeton.edu/15uf">Section 1.5</a> of
  * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
  * @param n number of objects
  * @author Robert Sedgewick
  * @author Kevin Wayne
  * @author Barry Becker - minor modifications
  */
class UnionFind(n: Int) {

  // parent[i] = parent of i
  private val parent: Array[Int] = Array.ofDim[Int](n)
  // size[i] = number of objects in subtree rooted at i
  private val size = Array.ofDim[Int](n)
  // number of components
  private var count = n
  var i = 0

  while (i < n) {
    parent(i) = i
    size(i) = 1
    i += 1
  }

  /** @return the number of components (between 1 and N) */
  def getCount: Int = count

  /** Returns the component identifier for the component containing site <tt>p</tt>.
    * @param site the integer representing one site
    * @return the component identifier for the component containing site <tt>p</tt>
    * @throws java.lang.IndexOutOfBoundsException unless 0 &lt;= p &lt; N
    */
  def find(site: Int): Int = {
    var p = site
    validate(p)
    while ( {
      p != parent(p)
    }) p = parent(p)
    p
  }

  /** validate that p is a valid index */
  private def validate(p: Int): Unit = {
    val N = parent.length
    if (p < 0 || p >= N) throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + N)
  }

  /** Are the two sites <tt>p</tt> and <tt>q</tt> in the same component?
    * @param p the integer representing one site
    * @param q the integer representing the other site
    * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt>
    *         are in the same component, and <tt>false</tt> otherwise
    * @throws java.lang.IndexOutOfBoundsException unless both 0 &lt;= p &lt; N and 0 &lt;= q &lt; N
    */
  def connected(p: Int, q: Int): Boolean = find(p) == find(q)

  /** Merges the component containing site<tt>p</tt> with the component
    * containing site <tt>q</tt>.
    * @param p the integer representing one site
    * @param q the integer representing the other site
    * @throws java.lang.IndexOutOfBoundsException unless both 0 &lt;= p &lt; N and 0 &lt;= q &lt; N
    */
  def union(p: Int, q: Int): Unit = {
    val rootP = find(p)
    val rootQ = find(q)
    if (rootP == rootQ) return
    // make smaller root point to larger one
    if (size(rootP) < size(rootQ)) {
      parent(rootP) = rootQ
      size(rootQ) += size(rootP)
    }
    else {
      parent(rootQ) = rootP
      size(rootP) += size(rootQ)
    }
    count -= 1
  }
}
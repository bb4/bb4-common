/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.expression

import scala.collection.mutable.ListBuffer


/**
  * Parses the text form of an expression into a tree representation.
  * See derived classes for specific sorts of expression parsers.
  * Called recursively to parse sub-expressions nested within parenthesis.
  * @author Barry Becker
  */
abstract class ExpressionParser(var opDef: OperatorsDefinition) {

  /** @param textExpression the expression to parse. Must not be null or empty.
    * @return the root node in the parsed expression tree.
    */
  def parse(textExpression: String): TreeNode = {
    val nodes = getNodesAtLevel(textExpression)
    makeTreeFromNodes(nodes)
  }

  /** Recursive method to find all the tree nodes for the terms at the current parenthesized level.
    * For example, given this expression
    * 2x^3 +  5(x + 3x^2) / (x - 1)
    * The items in []'s represent the array of nodes returned.
    * [2] [*] [x] [^] [3] [+] [5][*][x + 3x^2] [/] [x - 1]
    * The parts that were in ()'s become their own subtrees via recursive calls.
    * Throws Error if there is a syntax error causing the expression to be invalid.
    *
    * @param exp the expression to get the nodes at the current level for
    * @return array of nodes representing terms that the current level.
    */
  protected def getNodesAtLevel(exp: String): ListBuffer[TreeNode]

  /** @param exp the whole sup expression
    * @param pos location of lef parenthesis
    * @return location of matching right parenthesis
    */
  protected def findClosingParen(exp: String, pos: Int): Int = {
    var parenCount = 1
    var i = pos
    var ch = 0
    do {
      ch = exp.charAt(i)
      i += 1
      if (ch == LEFT_PAREN.symbol) {
        parenCount += 1
      }
      if (ch == RIGHT_PAREN.symbol) {
        parenCount -= 1
      }
    } while (!(ch == RIGHT_PAREN.symbol && parenCount == 0) && i < exp.length)

    if (ch != RIGHT_PAREN.symbol && i == exp.length)
      throw new IllegalStateException("Mismatched parenthesis in " + exp)
    i - 1
  }

  /** Parse a parenthesized sub expression recursively.
    * @return current token. May have been reset to "".
    */
  protected def processSubExpression(exp: String, pos: Int, token: String,
                                     closingParenPos: Int, nodes: ListBuffer[TreeNode]): String

  /** The token may represent several nodes because of implicit multiplication.
    * For example,
    * -4x should become  [-4] [times] [x]
    * -x should become [-1] [times] [x]
    * @param token the token to parse
    * @param nodes array of nodes that the token was parsed into.
    */
  protected def pushNodesForToken(token: String, nodes: ListBuffer[TreeNode]): Unit

  /** Converts a list of nodes to a single node by reducing them to
    * subtrees in order of operator precedence.
    */
  protected def makeTreeFromNodes(nodes: ListBuffer[TreeNode]): TreeNode

  protected def splice(nodes: ListBuffer[TreeNode], start: Int, num: Int, newNode: TreeNode): Unit = {
    var i = start
    while (i < start + num) {
      nodes.remove(start)
      i += 1
    }
    nodes.insert(start, newNode)
  }

  protected def isOperator(node: TreeNode, ops: Array[Operator]): Boolean = {
    if (node.getData == null || node.getData.length != 1) return false
    opDef.isOperator(node.getData.charAt(0))
  }
}

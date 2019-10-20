/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.expression.mathexpression

import com.barrybecker4.common.expression._
import com.barrybecker4.common.expression.{LEFT_PAREN, RIGHT_PAREN}
import scala.collection.mutable.ListBuffer


/**
  * Parses the text form of an expression (in x) into a tree representation.
  * Create a subclass for parsing a math expression.
  * @author Barry Becker
  */
class MathExpressionParser(opDef: OperatorsDefinition) extends ExpressionParser(opDef) {

  /** Recursive method to find all the tree nodes for the terms a the current level.
    * For example, given this expression
    * 2x^3 +  5(x + 3x^2) / (x - 1)
    * the items in []'s represent the array of nodes returned.
    * [2] [*] [x] [^] [3] [+] [5][*][x + 3x^2] [/] [x - 1]
    * The parts that were in {()'s become their own subtrees via recursive calls.
    * throws an Error if there is a syntax error causing the expression to be invalid
    * @param exp the expression to get the nodes at the current level for
    * @return array of nodes representing terms that the current level.
    */
  override protected def getNodesAtLevel(exp: String): ListBuffer[TreeNode] = {
    var pos = 0
    val nodes = ListBuffer[TreeNode]()
    var token = ""
    var ch = exp.charAt(pos)
    while (pos < exp.length && !(token == "" + RIGHT_PAREN.symbol)) {
      if (ch == ' ') {
        // spaces are ignored
      }
      else if (ch == LEFT_PAREN.symbol) {
        val closingParenPos = findClosingParen(exp, pos + 1)
        // this method will make the recursive call
        token = processSubExpression(exp, pos + 1, token, closingParenPos, nodes)
        pos = closingParenPos + 1
      }
      else if (ch == MINUS.symbol && token.length == 0 && opDef.isLastNodeOperator(nodes)) {
        // a leading minus sign
        token += ch
      }
      else if (isNumericChar(ch)) {
        token += ch
        if (token.contains("x")) {
          throw new Error("Cannot have numbers after x in a term " + token + " within " + exp)
        }
      }
      else if (ch == 'x') token += ch
      else if (opDef.isOperator(ch)) {
        pushNodesForToken(token, nodes)
        token = ""
        nodes.append(new TreeNode(ch, opDef))
      }
      else throw new Error("Unexpected character '" + ch + "' in expression: " + exp)
      pos += 1
      if (pos < exp.length) ch = exp.charAt(pos)
    }
    // add the final node
    pushNodesForToken(token, nodes)
    nodes
  }

  /** Parse a parenthesized sub expression recursively.
    * @return current token. May have been reset to "".
    */
  override protected def processSubExpression(exp: String, pos: Int,
                                              token: String, closingParenPos: Int,
                                              nodes: ListBuffer[TreeNode]): String = {
    val subExp = exp.substring(pos, closingParenPos)
    // recursive call for sub expression
    val subTree = parse(subExp)
    var resultToken = token
    subTree.hasParens = true
    if (token != null && token.length > 0) { // there was some leading token before the parenthesized expression.
      pushNodesForToken(token, nodes)
      resultToken = ""
      nodes.append(new TreeNode(TIMES.symbol, opDef))
    }
    else if (nodes.nonEmpty && nodes.last.hasParens)
      nodes.append(new TreeNode(TIMES.symbol, opDef))
    nodes.append(subTree)
    resultToken
  }

  /**
    * The token may represent several nodes because of implicit multiplication.
    * For example,
    * -4x should become  [-4] [times] [x]
    * -x should become [-1] [times] [x]
    * @param token the token to parse
    * @param nodes array of nodes that the token was parsed into.
    */
  override protected def pushNodesForToken(token: String, nodes: ListBuffer[TreeNode]): Unit = {
    if (token == null || token.length == 0) return
    val len = token.length
    if (token.charAt(len - 1) == 'x') {
      if (len > 1) {
        if (token.charAt(0) == MINUS.symbol) nodes.append(new TreeNode("-1", opDef))
        else nodes.append(getNodeForNumber(token.substring(0, len - 1)))
        nodes.append(new TreeNode(TIMES.symbol, opDef))
      }
      nodes.append(new TreeNode("x", opDef))
    }
    else nodes.append(getNodeForNumber(token))
  }

  /** Converts a list of nodes to a single node by reducing them to
    * subtrees in order of operator precedence.
    */
  override protected def makeTreeFromNodes(theNodes: ListBuffer[TreeNode]): TreeNode = {
    var nodes = theNodes
    for (ops <- opDef.getOperatorPrecedence) {
      System.out.println("nodes=" + nodes + " ops=" + ops.mkString(", "))
      nodes = reduceNodes(ops, nodes)
    }
    if (nodes.size > 1)
      throw new Error("Expected to have only one node after reducing, but have " + nodes.size + " : " + nodes)
    if (nodes.head.isOperator && nodes.head.children.isEmpty)
      throw new Error("Missing operands")
    nodes.head
  }

  /** Simplify the list of terms by evaluating the terms joined by the specified operators.
    * Reduce the nodes list to a single node and return it.
    * @param ops   list of operators that all have the same precedence.
    * @param nodes the list of nodes to reduce
    * @return same list of nodes, but reduced.
    */
  private def reduceNodes(ops: Array[Operator], nodes: ListBuffer[TreeNode]) = {
    var index = 1
    if (nodes.size == 2) throw new Error("Missing operand : " + nodes)
    while (index < nodes.size) if (isOperator(nodes(index), ops)) {
      nodes(index).children = Seq(nodes(index - 1), nodes(index + 1))
      if (nodes.size < index + 1) throw new Error("Not enough operands for operator in nodes=" + nodes)
      //System.out.println("before splice : " + nodes);
      splice(nodes, index - 1, 3, nodes(index))
      //System.out.println("after splice : " + nodes);
    }
    else index += 2
    nodes
  }

  private def isNumericChar(ch: Char) = (ch >= '0' && ch <= '9') || ch == '.'

  private def getNodeForNumber(token: String) = {
    val num: Double = token.toDouble
    if (num.isNaN) throw new IllegalStateException("Invalid number in expression: " + token)
    new TreeNode("" + num, opDef)
  }
}

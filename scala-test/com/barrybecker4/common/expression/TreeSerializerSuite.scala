package com.barrybecker4.common.expression

import org.scalatest.FunSuite
import com.barrybecker4.common.expression.TreeSerializer
import com.barrybecker4.common.expression.mathexpression.MathOperatorsDefinition

class TreeSerializerSuite extends FunSuite {

  val serializer = new TreeSerializer

  test("serialize expression") {
    val opDef = new MathOperatorsDefinition
    val node: TreeNode = new TreeNode("*", opDef)
    node.children = Seq(new TreeNode("x", opDef), new TreeNode("3", opDef))

    assertResult("x * 3") {
      serializer.serialize(node)
    }
  }
}

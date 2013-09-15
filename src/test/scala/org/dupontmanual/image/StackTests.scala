package org.dupontmanual.image

import org.scalatest.{ FunSuite, Matchers }

class StackTests extends FunSuite with Matchers {
  val e1 = EllipseFilled(Color.DeepSkyBlue, 70, 40)
  val e2 = EllipseFilled(Color.DarkGray, 20, 80)
  
  test("centered") {
    e1.stackOn(e2) shouldEqual Bitmap.fromWorkspace("/stacked/e1-e2.png")
    e2.slideUnder(e1) shouldEqual Bitmap.fromWorkspace("/stacked/e1-e2.png")
    e2.stackOn(e1) shouldEqual Bitmap.fromWorkspace("/stacked/e2-e1.png")
    e1.slideUnder(e2) shouldEqual Bitmap.fromWorkspace("/stacked/e2-e1.png")
    e1.stackOn(e2, 20, 10) shouldEqual Bitmap.fromWorkspace("/stacked/e1-e2-20-10.png")
  }
  
  test("aligns") {
    e1.stackOn(e2, XAlign.Left, YAlign.Bottom) shouldEqual Bitmap.fromWorkspace("/stacked/e1-e2-left-bottom.png")
    e2.stackOn(e1, XAlign.Center, YAlign.Top) shouldEqual Bitmap.fromWorkspace("/stacked/e2-e1-center-top.png")
    e1.stackOn(e2, XAlign.Right, YAlign.Center) shouldEqual Bitmap.fromWorkspace("/stacked/e1-e2-right-center.png")
  }

}
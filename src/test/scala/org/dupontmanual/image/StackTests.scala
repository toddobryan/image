package org.dupontmanual.image

import org.scalatest.{ FunSuite, Matchers }

class StackTests extends FunSuite with Matchers {
  val e1 = Ellipse(Color.DeepSkyBlue, 70, 40)
  val e2 = Ellipse(Color.DarkGray, 20, 80)
  val bg = Rectangle(Color.Gray, 300, 200)
  val c = Circle(Color.Aqua, 25)
  
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
  
  test("placeImage") {
    bg.placeImage(c, 150, 100) shouldEqual Bitmap.fromWorkspace("/stacked/bg-c-centered.png")
    bg.placeImage(c, 150, 100, XAlign.Left, YAlign.Top) shouldEqual Bitmap.fromWorkspace("/stacked/bg-c-top-left.png")
    bg.placeImage(c, 150, 100, XAlign.Right, YAlign.Center) shouldEqual Bitmap.fromWorkspace("/stacked/bg-c-center-right.png")
    bg.placeImage(c, 150, 100, XAlign.Center, YAlign.Bottom) shouldEqual Bitmap.fromWorkspace("/stacked/bg-c-bottom-center.png")
    // completely off-screen should be same object
    bg.placeImage(c, -25, -25) should be theSameInstanceAs bg
    bg.placeImage(c, 150, -30) should be theSameInstanceAs bg
    bg.placeImage(c, 325, 100) should be theSameInstanceAs bg
    bg.placeImage(c, 150, 225) should be theSameInstanceAs bg
    // partially off-screen checks
    bg.placeImage(c, 0, 0) shouldEqual Bitmap.fromWorkspace("/stacked/bg-c-center-at-0,0.png")
    bg.placeImage(c, 150, 190, XAlign.Center, YAlign.Top) shouldEqual Bitmap.fromWorkspace("/stacked/bg-c-top-at-150,190.png")
    bg.placeImage(c, 5, 100, XAlign.Right, YAlign.Center) shouldEqual Bitmap.fromWorkspace("/stacked/bg-c-right-at-5,100.png")
    bg.placeImage(c, 275, 25, XAlign.Left, YAlign.Bottom) shouldEqual Bitmap.fromWorkspace("/stacked/bg-c-bottom-left-at-275,25.png")    
  }

}
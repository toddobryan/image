package net.toddobryan.image

import org.scalatest.{ FunSuite, Matchers }

class StackTests extends FunSuite with Matchers {
  val e1 = Ellipse(Color.DeepSkyBlue, 70, 40)
  val e2 = Ellipse(Color.DarkGray, 20, 80)
  val bg = Rectangle(Color.Gray, 300, 200)
  val c = Circle(Color.Aqua, 25)
  val folder = "/fxStacked/"
  
  test("dimensions") {
    // Hacker is 48x48, TrainEngine is 129x44
    val s1 = Hacker.stackOn(TrainEngine, XAlign.Left, YAlign.Top)
    s1.width shouldEqual 129.0
    s1.height shouldEqual 48.0
    
  }
  
  test("centered") {
    e1.stackOn(e2) shouldEqual Bitmap.fromWorkspace(folder + "e1-e2.png")
    e2.slideUnder(e1) shouldEqual Bitmap.fromWorkspace(folder + "e1-e2.png")
    e2.stackOn(e1) shouldEqual Bitmap.fromWorkspace(folder + "e2-e1.png")
    e1.slideUnder(e2) shouldEqual Bitmap.fromWorkspace(folder + "e2-e1.png")
    e1.stackOn(e2, 20, 10) shouldEqual Bitmap.fromWorkspace(folder + "e1-e2-20-10.png")
  }
  
  test("aligns") {
    e1.stackOn(e2, Align.BottomLeft) shouldEqual Bitmap.fromWorkspace(folder + "e1-e2-bottom-left.png")
    e2.stackOn(e1, Align.Top) shouldEqual Bitmap.fromWorkspace(folder + "e2-e1-top.png")
    e1.stackOn(e2, Align.Right) shouldEqual Bitmap.fromWorkspace(folder + "e1-e2-right.png")
  }
  
  test("placeImage") {
    bg.placeImage(c, 150, 100) shouldEqual Bitmap.fromWorkspace(folder + "bg-c-centered.png")
    bg.placeImage(c, 150, 100, Align.TopLeft) shouldEqual Bitmap.fromWorkspace(folder + "bg-c-top-left.png")
    bg.placeImage(c, 150, 100, Align.Right) shouldEqual Bitmap.fromWorkspace(folder + "bg-c-right.png")
    bg.placeImage(c, 150, 100, Align.Bottom) shouldEqual Bitmap.fromWorkspace(folder + "bg-c-bottom.png")
    // completely off-screen should be same object
    bg.placeImage(c, -26, -26) should be theSameInstanceAs bg
    bg.placeImage(c, 150, -30) should be theSameInstanceAs bg
    bg.placeImage(c, 326, 100) should be theSameInstanceAs bg
    bg.placeImage(c, 150, 226) should be theSameInstanceAs bg
    // partially off-screen checks
    bg.placeImage(c, 0, 0) shouldEqual Bitmap.fromWorkspace(folder + "bg-c-center-at-0,0.png")
    bg.placeImage(c, 150, 190, Align.Top) shouldEqual Bitmap.fromWorkspace(folder + "bg-c-top-at-150,190.png")
    bg.placeImage(c, 5, 100, Align.Right) shouldEqual Bitmap.fromWorkspace(folder + "bg-c-right-at-5,100.png")
    bg.placeImage(c, 275, 25, Align.BottomLeft) shouldEqual Bitmap.fromWorkspace(folder + "bg-c-bottom-left-at-275,25.png")    
  }
}
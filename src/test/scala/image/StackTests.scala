package image

import org.scalatest.FunSuite

class StackTests extends FunSuite {
  val e1 = EllipseFilled(Color.deepSkyBlue, 70, 40)
  val e2 = EllipseFilled(Color.darkGray, 20, 80)
  
  test("centered") {
    assert(e1.stackOn(e2).sameBitmap(Bitmap.fromWorkspace("/stacked/e1-e2.png")))
    //assert(Image.sameBitmap(e2.slideUnder(e1), "/stacked/e1-e2.png"))
    //assert(Image.sameBitmap(e2.stackOn(e1), "/stacked/e2-e1.png"))
    //assert(Image.sameBitmap(e1.slideUnder(e2), "/stacked/e2-e1.png"))
    //assert(Image.sameBitmap(e1.stackOn(e2, 20, 10), "/stacked/e1-e2-20-10.png"))
  }
  
  test("aligns") {
    //assert(Image.sameBitmap(e1.stackOn(e2, XAlign.left, YAlign.bottom), "/stacked/e1-e2-left-bottom.png"))
    //assert(Image.sameBitmap(e2.stackOn(e1, XAlign.center, YAlign.top), "/stacked/e2-e1-center-top.png"))
    //assert(Image.sameBitmap(e1.stackOn(e2, XAlign.right, YAlign.center), "/stacked/e1-e2-right-center.png"))
  }

}
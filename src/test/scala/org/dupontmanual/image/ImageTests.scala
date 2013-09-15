package org.dupontmanual.image

import org.scalatest.FunSuite
import org.scalatest.Matchers

class ImageTests extends FunSuite with Matchers {
  val cat = Bitmap.fromWorkspace("/bitmaps/cat.png")
  val pizza = Bitmap.fromWorkspace("/bitmaps/pizza.png")
  
  test("toString") {
    cat.toString should endWith ("cat.png\")")
    pizza.toString should endWith ("pizza.png\")")
    Hacker.toString shouldEqual ("Hacker")
  }
  
  test("flip") {
    cat.flipHorizontal shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-flip-hor.png")
    cat.flipHorizontal.flipHorizontal shouldEqual cat
    cat.flipVertical shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-flip-ver.png")
    
    pizza.flipHorizontal shouldEqual Bitmap.fromWorkspace("/bitmaps/pizza-flip-hor.png")
    pizza.flipVertical shouldEqual Bitmap.fromWorkspace("/bitmaps/pizza-flip-ver.png")
    pizza.flipVertical.flipVertical shouldEqual pizza 
  }
  
  test("scale") {
    cat.scale(3,3) shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-scale3x3y.png")
    cat.scale(.5,.5) shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-scale.5x.5y.png")
    cat.scaleX(3) shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-scalex3.png")
    cat.scaleY(3) shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-scaley3.png")
  }
}
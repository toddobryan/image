package org.dupontmanual.image

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class ImageTests extends FunSuite with ShouldMatchers {
  val cat = Bitmap.fromWorkspace("/bitmaps/cat.png")
  val pizza = Bitmap.fromWorkspace("/bitmaps/pizza.png")
  
  test("toString") {
    cat.toString should endWith ("cat.png\")")
    pizza.toString should endWith ("pizza.png\")")
    Hacker.toString should be === ("Hacker")
  }
  
  test("flip") {
    cat.flipHorizontal should have (sameBitmapAs 
        (Bitmap.fromWorkspace("/bitmaps/cat-flip-hor.png")))
    cat.flipHorizontal.flipHorizontal should have (sameBitmapAs (cat))
    cat.flipVertical should have (sameBitmapAs 
        (Bitmap.fromWorkspace("/bitmaps/cat-flip-ver.png")))
    
    pizza.flipHorizontal should have (sameBitmapAs 
        (Bitmap.fromWorkspace("/bitmaps/pizza-flip-hor.png")))
    pizza.flipVertical should have (sameBitmapAs 
        (Bitmap.fromWorkspace("/bitmaps/pizza-flip-ver.png")))
    pizza.flipVertical.flipVertical should have (sameBitmapAs (pizza)) 
  }
  
  test("scale") {
    cat.scale(3,3) should have (sameBitmapAs 
        (Bitmap.fromWorkspace("/bitmaps/cat-scale3x3y.png")))
    cat.scale(.5,.5) should have (sameBitmapAs
        (Bitmap.fromWorkspace("/bitmaps/cat-scale.5x.5y.png")))
    cat.scaleX(3) should have (sameBitmapAs
        (Bitmap.fromWorkspace("/bitmaps/cat-scalex3.png")))
    cat.scaleY(3) should have (sameBitmapAs
        (Bitmap.fromWorkspace("/bitmaps/cat-scaley3.png")))
  }
}
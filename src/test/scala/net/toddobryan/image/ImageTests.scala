package net.toddobryan.image

import org.scalatest.FunSuite
import org.scalatest.Matchers
import org.scalatest.BeforeAndAfterAll
import scalafx.application.Platform

class ImageTests extends FunSuite with Matchers with BeforeAndAfterAll {
  println("Looking for cat...")
  val cat = Bitmap.fromWorkspace("/bitmaps/cat.png")
  println("Looking for pizza...")
  val pizza = Bitmap.fromWorkspace("/bitmaps/pizza.png")
  
  override def beforeAll() {
    initialize()
  }
  
  override def afterAll() {
    Platform.exit()
  }
  
  test("toString") {
    println("Testing toString...")
    cat.toString should endWith ("cat.png\")")
    pizza.toString should endWith ("pizza.png\")")
    Hacker.toString shouldEqual ("Hacker")
  }
  
  test("flip") {
    println("Testing flip...")
    println("Getting cat.flipHorizontal...")
    val catFlipH = cat.flipHorizontal
    println("Getting catFlipH from workspace...")
    val catFlipHPng = Bitmap.fromWorkspace("/bitmaps/cat-flip-hor.png")
    println("Comparing...")
    catFlipH shouldEqual catFlipHPng
    cat.flipHorizontal.flipHorizontal shouldEqual cat
    cat.flipVertical shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-flip-ver.png")
    
    pizza.flipHorizontal shouldEqual Bitmap.fromWorkspace("/bitmaps/pizza-flip-hor.png")
    pizza.flipVertical shouldEqual Bitmap.fromWorkspace("/bitmaps/pizza-flip-ver.png")
    pizza.flipVertical.flipVertical shouldEqual pizza 
  }
  
  test("scale") {
    println("Testing scale...")
    cat.scale(3,3) shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-scale3x3y.png")
    cat.scale(.5,.5) shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-scale.5x.5y.png")
    cat.scaleX(3) shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-scalex3.png")
    cat.scaleY(3) shouldEqual Bitmap.fromWorkspace("/bitmaps/cat-scaley3.png")
  }
}
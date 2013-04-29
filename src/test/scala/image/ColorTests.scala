package image

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class ColorTests extends FunSuite with ShouldMatchers {
  import Color._
  
  test("names") {
    Color.Burlywood.toString should be === "Color.Burlywood"
    Color.Honeydew.toString should be === "Color.Honeydew"
    Color.Yellow.toString should be === "Color.Yellow"
  }
  
  test("HTML colors show name for toString") {
    Color(255, 0, 255).toString should be === "Color.Magenta"
    Color(0x3c, 0xb3, 0x71).toString should be === "Color.MediumSeaGreen"
  }
  
  test("RGB values for colors that aren't HTML") {
    Color(50, 60, 70).toString should be === "Color(red = 50, green = 60, blue = 70)"
    Color(35, 0, 255).toString should be === "Color(red = 35, green = 0, blue = 255)"
  }
  
  test("RGBA values if alpha is not 255") {
    Color(50, 60, 70, 123).toString should be === "Color(red = 50, green = 60, blue = 70, alpha = 123)"
  }
  
  test("RGB values from 0-255") {
    val e1 = evaluating { Color(red = -5) } should produce [IllegalArgumentException]
    e1.getMessage should be === "requirement failed: red value must be between 0 and 255 inclusive"
    val e2 = evaluating { Color(red = 0, green = 260, blue = 125) } should produce [IllegalArgumentException]
    e2.getMessage should be === "requirement failed: green value must be between 0 and 255 inclusive"
    val e3 = evaluating { Color(blue = 256) } should produce [IllegalArgumentException]
    e3.getMessage should be === "requirement failed: blue value must be between 0 and 255 inclusive"
  }

}
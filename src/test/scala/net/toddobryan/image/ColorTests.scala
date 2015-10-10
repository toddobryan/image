package net.toddobryan.image

import org.scalatest.FunSuite
import org.scalatest.Matchers

class ColorTests extends FunSuite with Matchers {
  
  test("names") {
    Color.Burlywood.toString shouldEqual "Color.Burlywood"
    Color.Honeydew.toString shouldEqual "Color.Honeydew"
    Color.Yellow.toString shouldEqual "Color.Yellow"
  }
  
  test("HTML colors show name for toString") {
    Color(255, 0, 255).toString shouldEqual "Color.Magenta"
    Color(0x3c, 0xb3, 0x71).toString shouldEqual "Color.MediumSeaGreen"
  }
  
  test("RGB values for colors that aren't HTML") {
    Color(50, 60, 70).toString shouldEqual "Color(red = 50, green = 60, blue = 70)"
    Color(35, 0, 255).toString shouldEqual "Color(red = 35, green = 0, blue = 255)"
  }
  
  test("RGBA values if opacity is not 1.0") {
    Color(50, 60, 70, 0.5).toString shouldEqual "Color(red = 50, green = 60, blue = 70, opacity = 0.5)"
  }
  
  test("RGB values from 0-255") {
    the [IllegalArgumentException] thrownBy { Color(red = -5) } should have message 
        "requirement failed: red value must be between 0 and 255 inclusive"
    the [IllegalArgumentException] thrownBy { Color(red = 0, green = 260, blue = 125) } should have message 
        "requirement failed: green value must be between 0 and 255 inclusive"
    the [IllegalArgumentException] thrownBy { Color(blue = 256) } should have message
        "requirement failed: blue value must be between 0 and 255 inclusive"
  }

}
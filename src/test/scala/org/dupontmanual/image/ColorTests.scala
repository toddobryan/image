package org.dupontmanual.image

import org.scalatest.FunSuite
import org.scalatest.Matchers

class ColorTests extends FunSuite with Matchers {
  import Color._
  
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
    val e1 = evaluating { Color(red = -5) } should produce [IllegalArgumentException]
    e1.getMessage shouldEqual "requirement failed: red value must be between 0 and 255 inclusive"
    val e2 = evaluating { Color(red = 0, green = 260, blue = 125) } should produce [IllegalArgumentException]
    e2.getMessage shouldEqual "requirement failed: green value must be between 0 and 255 inclusive"
    val e3 = evaluating { Color(blue = 256) } should produce [IllegalArgumentException]
    e3.getMessage shouldEqual "requirement failed: blue value must be between 0 and 255 inclusive"
  }

}
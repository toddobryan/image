package net.toddobryan.image

import org.scalatest.{ FunSuite, Matchers }

import Font._;

class TextTests extends FunSuite with Matchers {
  // test all BuiltIn fonts (which are actually DejaVu fonts)
  test("toString") {
    Font(Serif, Plain, 24).toString shouldEqual "Font(Font.Serif, Font.Plain, 24.0)"
    Font(Serif, Italic, 18).toString shouldEqual "Font(Font.Serif, Font.Italic, 18.0)"
  }
}
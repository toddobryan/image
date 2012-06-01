package image

import math.Pi

import org.scalatest.FunSuite

import AngleUnit._

class AngleTest extends FunSuite {
  test("angle equality") {
    assert(45.degrees === Angle(45, degrees))
    assert((Pi / 2).radians === Angle(Pi / 2, radians))
    assert((Pi / 2).radians === 90.degrees)
  }
  
  test("reference angles") {
    assert(360.degrees.refAngle === 0.degrees)
    assert((7.0 * Pi / 2.0).radians.refAngle === (-Pi / 2.0).radians)
    assert(270.degrees.refAngle === -90.degrees)
    assert((-3.0 * Pi / 2.0).radians.refAngle === (Pi / 2.0).radians)
  }
  
  test("special angle trig") {
    assert(90.degrees.sin === 1)
    assert((-30).degrees.sin === -0.5)
    assert(135.degrees.tan === -1)
    assert((-240).degrees.cos == -0.5)
  }
}
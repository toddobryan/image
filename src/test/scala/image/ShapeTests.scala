package image

import org.scalatest.FunSuite

import math.Pi

import java.io.File


class ShapeTests extends FunSuite {
  test("circles") {
    assert(Image.sameBitmap(CircleFilled(Color.aquamarine, 50), 
        "/shapes/circle-50-aquamarine.png"))
    assert(Image.sameBitmap(CircleOutlined(Color.black, 10),
        "/shapes/circle-10-black.png"))
    assert(Image.sameBitmap(CircleOutlined(Pen(Color.blue, 5), 25),
        "/shapes/circle-25-blue.png"))
  }
  
  test("ellipses") {
    assert(Image.sameBitmap(EllipseFilled(Color.blueViolet, 40, 80),
        "/shapes/ellipse-40-80-blueViolet.png"))
    assert(Image.sameBitmap(EllipseOutlined(Color.goldenrod, 80, 20),
        "/shapes/ellipse-80-20-goldenrod.png"))
    assert(Image.sameBitmap(EllipseOutlined(Pen(Color.pink, 20), 100, 50),
        "/shapes/ellipse-100-50-pink.png"))
  }
  
  test("wedges") {
    assert(Image.sameBitmap(CircularArc(Pen(Color.green, 3), 50, 30.degrees, 120.degrees),
        "/shapes/arc-50-30-120-green.png"))
    assert(Image.sameBitmap(CircularSegment(Color.cyan, 25, -20.degrees, 220.degrees),
        "/shapes/seg-25-20-220-cyan.png"))
    assert(Image.sameBitmap(CircularWedge(Color.red, 30, (Pi / 6).radians, (2 * Pi / 3).radians),
        "/shapes/wedge-30-30-120-red.png"))
  }
  
  test("elliptical wedges") {
    assert(Image.sameBitmap(EllipticalArc(Pen(Color.fireBrick, 3), 50, 80, 30.degrees, 120.degrees),
        "/shapes/ell-arc-50-80-30-120-fireBrick.png"))
    assert(Image.sameBitmap(EllipticalSegment(Color.tomato, 35, 25, -20.degrees, 220.degrees),
        "/shapes/ell-seg-35-25-20-220-tomato.png"))
    assert(Image.sameBitmap(EllipticalWedge(Color.orange, 30, 100, (Pi / 6).radians, (2 * Pi / 3).radians),
        "/shapes/ell-wedge-30-100-30-120-red.png")) 
  }
  
  test("polygonal") {
    assert(Image.sameBitmap(
        Polygon(Color.burlywood, Point(0, 0), Point(-10, 20), Point(60, 0), Point(-10, -20)),
        "/shapes/polygon-arrow.png"))
    assert(Image.sameBitmap(
        Polygon(Color.plum, Point(0, 0), Point(0, 40), Point(20, 40), 
            Point(20, 60), Point(40, 60), Point(40, 20), Point(40, 20), 
            Point(20, 20), Point(20, 0)),
        "/shapes/polygon-plum-tetris.png"))
    
  }
}
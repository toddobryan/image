package org.dupontmanual.image

import org.scalatest.FunSuite
import org.scalatest.Matchers

import math.Pi

import java.io.File

class ShapeTests extends FunSuite with Matchers {
  initialize()
  
  test("circles") {
    Circle(Color.Aquamarine, 50) shouldEqual Bitmap.fromWorkspace("/shapes/circle-50-aquamarine.png")
    Circle.outlined(Color.Black, 10) shouldEqual Bitmap.fromWorkspace("/shapes/circle-10-black.png")
    Circle(Pen(Color.Blue, 5), 25) shouldEqual Bitmap.fromWorkspace("/shapes/circle-25-blue.png")
  }
  
  test("ellipses") {
    Ellipse(Color.BlueViolet, 40, 80) shouldEqual 
        Bitmap.fromWorkspace("/shapes/ellipse-40-80-blueViolet.png")
    Ellipse.outlined(Color.Goldenrod, 80, 20) shouldEqual 
        Bitmap.fromWorkspace("/shapes/ellipse-80-20-goldenrod.png")
    Ellipse(Pen(Color.Pink, 20), 100, 50) shouldEqual 
        Bitmap.fromWorkspace("/shapes/ellipse-100-50-pink.png")
  }
  
  test("wedges") {
    CircularArc(Pen(Color.Green, 3), 50, 30.degrees, 120.degrees) shouldEqual 
        Bitmap.fromWorkspace("/shapes/arc-50-30-120-green.png")
    CircularSegment(Color.Cyan, 25, -20.degrees, 220.degrees) shouldEqual 
        Bitmap.fromWorkspace("/shapes/seg-25-20-220-cyan.png")
    CircularSector(Color.Red, 30, (Pi / 6).radians, (2 * Pi / 3).radians) shouldEqual 
        Bitmap.fromWorkspace("/shapes/wedge-30-30-120-red.png")
  }
  
  test("elliptical wedges") {
    EllipticalArc(Pen(Color.FireBrick, 3), 50, 80, 50.degrees, 120.degrees) shouldEqual 
        Bitmap.fromWorkspace("/shapes/ell-arc-50-80-50-120-brick.png")
    EllipticalSegment(Color.Tomato, 35, 25, -20.degrees, 270.degrees) shouldEqual 
        Bitmap.fromWorkspace("/shapes/ell-seg-35-25-20-270-tomato.png")
    EllipticalSector(Color.Orange, 30, 100, (Pi / 6).radians, (3 * Pi / 4).radians) shouldEqual 
        Bitmap.fromWorkspace("/shapes/ell-wedge-30-100-30-135-orange.png")
  }
  
  test("polygonal") {
    Polygon(Color.Burlywood, Point(0, 0), Point(-10, 20), Point(60, 0), Point(-10, -20)) shouldEqual 
        Bitmap.fromWorkspace("/shapes/polygon-arrow.png")
    Polygon(Color.Plum, Point(0, 0), Point(0, 40), Point(20, 40), 
            Point(20, 60), Point(40, 60), Point(40, 20), Point(40, 20), 
            Point(20, 20), Point(20, 0)) shouldEqual 
        Bitmap.fromWorkspace("/shapes/polygon-plum-tetris.png")
  }
  
  test("rectangle") {
    Rectangle(Color.Aquamarine, 100, 70) shouldEqual 
        Bitmap.fromWorkspace("/shapes/rectangle-100-70-aquamarine.png")
    Rectangle.outlined(Color.Red, 30, 50) shouldEqual 
        Bitmap.fromWorkspace("/shapes/rectangle-30-50-red.png")
    Rectangle(Color.Maroon, 60, 80) shouldEqual 
        Bitmap.fromWorkspace("/shapes/rectangle-60-80-maroon.png")
  }
  
  test("square") {
    Square(Color.Green, 30) shouldEqual 
        Bitmap.fromWorkspace("/shapes/square-30-green.png")
    Square(Color.Purple, 60) shouldEqual 
        Bitmap.fromWorkspace("/shapes/square-60-purple.png")
    Square.outlined(Color.Black, 70) shouldEqual 
        Bitmap.fromWorkspace("/shapes/square-70-black.png")
  } 
}
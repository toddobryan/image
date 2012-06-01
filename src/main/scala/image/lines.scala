package image

import math.min

import java.awt.Paint

object Poly {
  def topLeft(pts: List[Point]): Point = pts match {
    case Nil => throw new Exception("can't create Polygon with no vertices")
    case p :: Nil => p
    case p :: rest => {
      val tlOfRest = topLeft(rest)
      Point(min(p.x, tlOfRest.x), min(p.y, tlOfRest.y))
    }
  }

  def shape(vertices: List[Point], close: Boolean) = {
    val tl = Poly.topLeft(vertices)
    val path = new java.awt.geom.Path2D.Double
    val transVs = (if (close) vertices else vertices.tail).map(p => p.translate(-tl.x, -tl.y))
    val start = if (close) vertices.last else vertices.head
    path.moveTo(start.x, start.y)
    for (v <- transVs) {
      path.lineTo(v.x, v.y)
    }
    path    
  }
}

class Polygon(paint: Paint, vertices: List[Point]) extends ShapeFilled(paint) {
  val awtShape = Poly.shape(vertices, true)
}

object Polygon {  
  def apply(paint: Paint, vertices: Point*) = new Polygon(paint, vertices.toList) 
}

class Polyline(pen: Pen, vertices: List[Point]) extends ShapeOutlined(pen) {
  val awtShape = Poly.shape(vertices, false)
}

object Polyline {
  def apply(pen: Pen, vertices: Point*) = new Polyline(pen, vertices.toList)
  def apply(color: java.awt.Color, vertices: Point*) = new Polyline(Pen(color), vertices.toList)
}


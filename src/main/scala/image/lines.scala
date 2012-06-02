package image

import math.min

import java.awt.Paint

private[image] object Poly {
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

private[image] class Polygon(paint: Paint, vertices: List[Point]) extends ShapeFilled(paint) {
  val awtShape = Poly.shape(vertices, true)
  override def toString = "Polygon(%s, %s)".format(paint, vertices.mkString(", "))
}

object Polygon {  
  def apply(paint: Paint, vertices: Point*) = new Polygon(paint, vertices.toList) 
}

private[image] class Polyline(pen: Pen, vertices: List[Point]) extends ShapeOutlined(pen) {
  val awtShape = Poly.shape(vertices, false)
  override def toString = "Polyline(%s, %s)".format(pen, vertices.mkString(", "))
}

object Polyline {
  def apply(pen: Pen, vertices: Point*) = new Polyline(pen, vertices.toList)
  def apply(color: java.awt.Color, vertices: Point*) = new Polyline(Pen(color), vertices.toList)
}


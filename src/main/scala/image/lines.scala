package image

import math.{min, Pi}

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
    val transVs = vertices.map(p => p.translate(-tl.x, -tl.y))
    val start = if (close) transVs.last else transVs.head
    path.moveTo(start.x, start.y)
    for (v <- (if (close) transVs else transVs.tail)) {
      path.lineTo(v.x, v.y)
    }
    path    
  }
  
  def regular(sideLength: Double, numSides: Int): List[Point] = {
    if (numSides < 3) throw new Exception("a polygon must have at least three sides")
    val radius = sideLength / (2.0 * (Pi / numSides).radians.sin)
    val offsetAngle = {
      if (numSides % 2 == 1) (-Pi / 2).radians
      else if (numSides % 4 == 0) (-Pi / numSides).radians
      else 0.radians
    }
    val polarVertices = (0 to (numSides - 1)).toList.map(n => PointPolar(radius, (n * 2.0 * Pi / numSides).radians + offsetAngle))
    polarVertices.map(_.toCartesian)
  }
}

private[image] class Polygon(paint: Paint, vertices: List[Point]) extends ShapeFilled(paint) {
  val awtShape = Poly.shape(vertices, true)
  override def toString = "Polygon(%s, %s)".format(paint, vertices.mkString(", "))
}

object Polygon {  
  def apply(
      paint: Paint, 
      vertex1: Point, vertex2: Point, vertex3: Point, 
      restOfVertices: Point*): Image = {
    new Polygon(paint, vertex1 :: vertex2 :: vertex3 :: restOfVertices.toList) 
  }
}

private[image] class Polyline(pen: Pen, vertices: List[Point]) extends ShapeOutlined(pen) {
  val awtShape = Poly.shape(vertices, false)
  override def toString = "Polyline(%s, %s)".format(pen, vertices.mkString(", "))
}

object Polyline {
  def apply(pen: Pen, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    new Polyline(pen, vertex1 :: vertex2 :: restOfVertices.toList)
  }
  def apply(color: java.awt.Color, vertex1: Point, vertex2: Point, restOfVertices: Point*): Image = {
    Polyline(Pen(color), vertex1, vertex2, restOfVertices: _*)
  }
}

object RegularPolygonFilled {
  def apply(paint: Paint, sideLength: Double, numSides: Int): Image = {
    new Polygon(paint, Poly.regular(sideLength, numSides))
  }
}

object RegularPolygonOutlined {
  def apply(pen: Pen, sideLength: Double, numSides: Int): Image = {
    val vts = Poly.regular(sideLength, numSides)
    new Polyline(pen, vts.last :: vts)
  }
  def apply(color: java.awt.Color, sideLength: Double, numSides: Int): Image = {
    RegularPolygonOutlined(Pen(color), sideLength, numSides)
  }
}



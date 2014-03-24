package org.dupontmanual.image

import scalafx.scene.shape.{ Shape, StrokeLineCap, StrokeLineJoin, StrokeType }

/**
 * represents the attributes of a pen used for drawing the lines that outline
 * images.
 * 
 * @param paint the paint that the pen leaves behind
 * @param width the width of the pen perpendicular to the direction of drawing
 * @param cap the style of end cap used on lines
 * @param join the style used when two lines meet
 * @param dash if present, the pixel pattern for dashed lines
 * 
 * All parameters are optional, and only parameters that differ from their
 * default values need to be included when constructing a `Pen`.
 */
case class Pen(
    paint: Paint = Color.Black, 
    width: Double = 1.0,
    edge: Edge = Edge.Centered,
    cap: Cap = Cap.Square, 
    join: Join = Join.Bevel, 
    dash: Option[Dash] = None) {
  
  override def toString: String = {
    /** if prevArgs are included, we need a comma before this arg */
    def maybeComma(prevArgs: String): String = if (prevArgs != "") ", " else ""
    val sPaint = if (paint != Color.Black) s"paint = $paint" else ""
    val sWidth = if (width != 0) s"${maybeComma(sPaint)}width = $width" else ""
    val sEdge = if (edge != Edge.Centered) s"${maybeComma(sPaint + sWidth)}edge = $edge" else ""
    val sCap = if (cap != Cap.Square) s"${maybeComma(sPaint + sWidth + sEdge)}cap = $cap" else ""
    val sJoin = if (join != Join.Bevel) s"${maybeComma(sPaint + sWidth + sEdge + sCap)}join = $join" else ""
    val sDash = dash.map((d: Dash) => s"${maybeComma(sPaint + sWidth + sEdge + sCap + sJoin)}dash = $d").getOrElse("")
    s"Pen($sPaint$sWidth$sEdge$sCap$sJoin$sDash)"
  }
  
  private[image] def applyToShape(shape: Shape) {
	shape.stroke = paint.fxPaint
	shape.strokeWidth = width
	shape.strokeType = edge.toStrokeType
	shape.strokeLineCap = cap.toStrokeLineCap
	shape.strokeLineJoin = join.toStrokeLineJoin
	if (dash.isDefined) {
	  val theDash = dash.get
	  shape.strokeDashOffset = theDash.offset
	  shape.strokeDashArray = theDash.pattern.map(new java.lang.Double(_))
	}
  }
}

object Pen {
  val Transparent = Pen(paint = Color.Transparent, width = 0.0)
}

/**
 * represents the options for decorating the ends of unclosed
 * subpaths and dash segments in a shape consisting of many lines.
 */
private[image] sealed abstract class Cap {
  private[image] def toStrokeLineCap: StrokeLineCap
}

/**
 * companion object with the three decoration types
 */
object Cap {
  /** ends line segments with no decoration */
  object None extends Cap {
    private[image] def toStrokeLineCap = StrokeLineCap.BUTT
    override def toString = "Cap.None"
  }
  
  /** ends segments with a round decoration half the radius of the `[[Pen]]` */
  object Round extends Cap {
    private[image] def toStrokeLineCap = StrokeLineCap.ROUND
    override def toString = "Cap.Round"
  }
  
  /** ends segments with a square decoration half the radius of the `[[Pen]]` */ 
  object Square extends Cap {
    private[image] def toStrokeLineCap = StrokeLineCap.SQUARE
    override def toString = "Cap.Square"
  }
}

/** parent class for style to use when lines are joined */
private[image] sealed abstract class Join {
  private[image] def toStrokeLineJoin: StrokeLineJoin
}

/** companion class containing the three styles used for joining lines */
object Join {
  /** this style "cuts the corner" where two lines meet */
  object Bevel extends Join {
    private[image] def toStrokeLineJoin = StrokeLineJoin.BEVEL
    /** returns `"Join.Bevel"` */
    override def toString = "Join.Bevel"
  }
  /** this style extends lines until they meet at a point */
  object Miter extends Join {
    private[image] def toStrokeLineJoin = StrokeLineJoin.MITER
    /** returns `"Join.Miter"` */
    override def toString = "Join.Miter"
  }
  /** this style rounds the corner where two lines meet */
  object Round extends Join {
    private[image] def toStrokeLineJoin = StrokeLineJoin.ROUND
    /** returns `"Join.Round"` */
    override def toString = "Join.Round"
  }
}


private[image] sealed abstract class Edge {
  private[image] def toStrokeType: StrokeType
}

object Edge {
  object Inside extends Edge {
    private[image] def toStrokeType = StrokeType.INSIDE
    override def toString = "Edge.Inside"
  }
  
  object Outside extends Edge {
    private[image] def toStrokeType = StrokeType.OUTSIDE
    override def toString = "Edge.Outside"
  }
  
  object Centered extends Edge {
    private[image] def toStrokeType = StrokeType.CENTERED
    override def toString = "Edge.Centered"
  }
}

/**
 * a class for representing the characteristics of dashed lines
 */
case class Dash(pattern: List[Double], offset: Double)

/**
 * a companion object with common dash patterns and an apply
 * method for creating dash patterns with zero offset
 */
object Dash {
  def apply(lengths: Double*) = new Dash(lengths.toList, 0)
  
  val Short = Dash(3, 2)
  val Long = Dash(5, 4)
  val Dot = Dash(1, 2)
  val DotDash = Dash(1, 3, 4, 3)
}


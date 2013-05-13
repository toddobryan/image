package org.dupontmanual.image

import java.awt.{BasicStroke, Stroke}

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
    width: Double = 0.0, 
    cap: Cap = Cap.Square, 
    join: Join = Join.Bevel, 
    dash: Option[Dash] = None) {
  /** returns a java.awt.Stroke representing this `Pen` */
  private[image] def asStroke: Stroke = {
    val (dashPatt, dashPhase) = dash.map(d => (d.patt, d.off)).getOrElse((null, 0.0f))
    new BasicStroke(width.toFloat, cap.toBsCap, join.toBsJoin, 10.0f, dashPatt, dashPhase)
  }
  
  override def toString: String = {
    /** if prevArgs are included, we need a comma before this arg */
    def maybeComma(prevArgs: String): String = if (prevArgs != "") ", " else ""
    val sPaint = if (paint != Color.Black) s"paint = $paint" else ""
    val sWidth = if (width != 0) s"${maybeComma(sPaint)}width = $width" else ""
    val sCap = if (cap != Cap.Square) s"${maybeComma(sPaint + sWidth)}cap = $cap" else ""
    val sJoin = if (join != Join.Bevel) s"${maybeComma(sPaint + sWidth + sCap)}join = $join" else ""
    val sDash = dash.map((d: Dash) => s"${maybeComma(sPaint + sWidth + sCap + sJoin)}dash = $d").getOrElse("")
    s"Pen($sPaint$sWidth$sCap$sJoin$sDash)"
  }
}


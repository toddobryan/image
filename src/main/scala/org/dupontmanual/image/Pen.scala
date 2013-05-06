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
 */
case class Pen(paint: Paint, width: Double, cap: Cap, join: Join, dash: Option[Dash]) {
  /** returns a java.awt.Stroke representing this `Pen` */
  private[image] def asStroke: Stroke = {
    val (dashPatt, dashPhase) = dash.map(d => (d.patt, d.off)).getOrElse((null, 0.0f))
    new BasicStroke(width.toFloat, cap.toBsCap, join.toBsJoin, 10.0f, dashPatt, dashPhase)
  }
}

/** a factory for `Pen` objects */
object Pen {
  /** 
   * returns a `Pen` that will draw one pixel width in the given `color`,
   * regardless of the magnification of the image.
   */ 
  def apply(color: Color): Pen = Pen(color, 0.0)
  /**
   * returns a `Pen` of the given `color` and `width`. Cap style is `Cap.None`,
   * Join style is `Join.Bevel`, and the line is not dashed.
   */
  def apply(color: Color, width: Double): Pen = Pen(color, width, Cap.None, Join.Bevel, None)
}
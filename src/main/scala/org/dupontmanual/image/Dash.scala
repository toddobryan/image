package org.dupontmanual.image

/**
 * a class for representing the characteristics of dashed lines
 */
class Dash(pattern: List[Double], offset: Double) {
  private[image] val patt: Array[Float] = pattern.map(_.toFloat).toArray
  private[image] val off: Float = offset.toFloat
}

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

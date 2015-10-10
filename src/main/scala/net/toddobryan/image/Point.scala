package net.toddobryan.image

/**
 * represents Cartesian points, with (0, 0) in the top left,
 * x-values increasing to the right, and y-values increasing as
 * you go down.
 * 
 * @param x the x coordinate
 * @param y the y coordinate
 */
case class Point(x: Double, y: Double) {
  /** 
   * returns a new `Point` that is the result of translating
   * this `Point` `dx` pixels to the right and `dy` pixels down 
   */
  def translate(dx: Double, dy: Double) = Point(x + dx, y + dy)
}

/**
 * represents polar points
 */
case class PointPolar(r: Double, theta: Angle) {
  /** returns the Cartesian point equivalent to this polar point */
  def toCartesian: Point = Point(r * theta.cos, r * theta.sin)
}

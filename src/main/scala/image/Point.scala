package image

case class Point(x: Double, y: Double) {
  def translate(dx: Double, dy: Double) = Point(x + dx, y + dy)
}
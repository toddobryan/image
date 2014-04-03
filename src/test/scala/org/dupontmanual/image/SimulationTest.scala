package org.dupontmanual.image

case class StationaryBall(point: Point) extends World[StationaryBall] {
  override def asImage(): Image = {
    Rectangle(Color.White, this.width, this.height).placeImage(Circle(Color.Blue, 10), point.x, point.y)
  }
  
  override def afterMouseClicked(x: Double, y: Double): StationaryBall = {
    StationaryBall(Point(x, y))
  }
  
  override def afterKeyPressed(key: String) = {
    val newPoint = key match {
      case "LEFT" => Point(point.x - 1, point.y)
      case "RIGHT" => Point(point.x + 1, point.y)
      case "UP" => Point(point.x, point.y - 1)
      case "DOWN" => Point(point.x, point.y + 1)
    }
    StationaryBall(newPoint)
  }
}

class SimulationTest {

}
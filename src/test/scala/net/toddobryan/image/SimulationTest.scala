package net.toddobryan.image

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

case class BouncingBall(n: Int) extends World[BouncingBall] {
  override val width: Int = 500
  override val height: Int = 500
  
  override def asImage(): Image = {
    val frame = n % 900
    val y = if (frame < 450) frame + 25 else 475 - frame % 450
    Rectangle(Color.Blue, this.width, this.height).placeImage(Circle(Color.Green, 25), 250, y)
  }
  
  override def afterTick(): BouncingBall = {
    BouncingBall(n + 2)
  }
}

object SimulationTest extends App {
  new Simulation(new BouncingBall(0), 5).run()
}
package org.dupontmanual.image

case class BallWorld(pt: Point) extends World[BallWorld] {
  override def asImage(): Image = {
    RectangleFilled(Color.White, this.width, this.height).placeImage(CircleFilled(Color.Blue, 10), pt.x, pt.y)
  }
  
  override def afterTick(): BallWorld = {
    BallWorld(Point(this.pt.x, this.pt.y + 1))
  }
}

object SimulationTest extends App {
  new Simulation(BallWorld(Point(100, 0)), 20).run()
}
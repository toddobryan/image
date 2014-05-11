package org.dupontmanual.image

case class BallWorld(pt: Point) extends World[BallWorld] {
  override def afterTick(): BallWorld = {
    BallWorld(Point(this.pt.x, this.pt.y + 1))
  }
}

object SimulationTest extends App {
  new Simulation(BallWorld(Point(100, 100)), 20)
}
package org.dupontmanual.image

import scala.util.Random

case class SnakeWorld(segLocs: List[Point], dir: String, foodLoc: Point) extends World[SnakeWorld] {
  override val width: Int = 300
  override val height: Int = 400
  
  override def asImage(): Image = {
    val food: Image = Rectangle(Color.Gray, this.width, this.height).placeImage(Square(Color.Green, 10), foodLoc.x, foodLoc.y)
    segLocs.:\(food)((pt: Point, img: Image) => img.placeImage(Circle(Color.Blue, 10), pt.x, pt.y))
  }
  
  override def afterKeyPressed(key: String): SnakeWorld = {
    println(key)
    if (List("UP", "DOWN", "LEFT", "RIGHT").contains(key)) {
      this.copy(dir = key)
    } else {
      this
    }
  }
  
  override def afterTick(): SnakeWorld = {
    val oldHead = this.segLocs.head
    val newHead = this.dir match {
      case "UP" => Point(oldHead.x, oldHead.y - 20)
      case "DOWN" => Point(oldHead.x, oldHead.y + 20)
      case "LEFT" => Point(oldHead.x - 20, oldHead.y)
      case "RIGHT" => Point(oldHead.x + 20, oldHead.y)
      case _ => oldHead
    }
    if (canEatFood()) {
      this.copy(segLocs = newHead :: this.segLocs, foodLoc = Point(Random.nextInt(this.width), Random.nextInt(this.height)))
    } else {
      this.copy(segLocs = newHead :: this.segLocs.init)
    }
  }
  
  def canEatFood(): Boolean = {
    def distance(p1: Point, p2: Point): Double = {
      math.sqrt(math.pow(p1.x - p2.x, 2) + math.pow(p1.y - p2.y, 2))
    }
    distance(this.segLocs.head, this.foodLoc) <= 20
  }
}

object Snake extends App {
  new Simulation(SnakeWorld(List(Point(50, 50)), "Right", Point(100, 100)), 5).run()
}
package org.dupontmanual.image
import scala.util.Random

object SnakeTest extends App {
  case class SnakeWorld(direction: String, segLocs: List[Point], foodLoc: Point)
      extends World[SnakeWorld] {
    override val width: Int = 600
    override val height: Int = 400
    
    override def asImage(): Image = {
      drawSnake(segLocs, foodLoc)
    }
    
    override def afterKeyPressed(key: String): SnakeWorld = {
      if (List("UP", "DOWN", "LEFT", "RIGHT").contains(key)) {
        this.copy(direction = key)
      } else {
        this
      }
    }
    
    override def afterTick(): SnakeWorld = {
      if (distance(segLocs.head, foodLoc) < 20) {
        this.copy(segLocs = newPointInDir(segLocs.head, direction) :: segLocs, foodLoc = Point(Random.nextInt(width), Random.nextInt(height)))
      } else {
        this.copy(segLocs = moveSnake(segLocs, direction))
      }
    }
  }
  
  val background: Image = Rectangle(Color.Black, 600, 400)
  
  val segPict: Image = Circle(Color.Green, 10)
  
  val foodPict: Image = Square(Color.Red, 10)
  
  /** given the locations for the snake's segments and the food location,
      produces an image of the world */
  def drawSnake(segs: List[Point], food: Point): Image = segs match {
    case Nil => background.placeImage(foodPict, food.x, food.y)
    case head :: tail => drawSnake(tail, food).placeImage(segPict, head.x, head.y)
  }
  
  /** given the locations for the snake's segments and the direction,
      produces the locations for the segments after moving the snake in the direction */
  def moveSnake(segs: List[Point], dir: String): List[Point] = {
    newPointInDir(segs.head, dir) :: segs.init
  }
  
  /** given a Point and a direction, produces a point 20 pixels in the given direction */
  def newPointInDir(pt: Point, dir: String): Point = {
    val dx: Int = dir match {
      case "LEFT" => -20
      case "RIGHT" => 20
      case _ => 0
    }
    val dy: Int = dir match {
      case "UP" => -20
      case "DOWN" => 20
      case _ => 0
    }
    Point(pt.x + dx, pt.y + dy)
  }
  
  def distance(p1: Point, p2: Point): Double = {
    math.sqrt(math.pow(p1.x - p2.x, 2) + math.pow(p1.y - p2.y, 2))
  }
  
  val simpleWorld: SnakeWorld = SnakeWorld("RIGHT", List(Point(50, 50), Point(30, 30)), Point(100, 100))
  
  new Simulation(simpleWorld, 5).run()
}
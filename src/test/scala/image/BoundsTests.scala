package image

import org.scalatest.FunSuite

class BoundsTests extends FunSuite {
  val e1 = EllipseFilled(Color.black, 60, 20)
  val e2 = EllipseFilled(Color.blue, 30, 50)
  
  test("centered") {
    assert(e1.stackOn(e2).bounds === Bounds(Point(0, 0), Point(60, 50)))
    assert(e1.stackOn(e2, 10, 0).bounds === Bounds(Point(0, 0), Point(60, 50)))
    assert(e1.stackOn(e2, 20, 0).bounds === Bounds(Point(0, 0), Point(65, 50)))
    assert(e1.stackOn(e2, 30, 0).bounds === Bounds(Point(0, 0), Point(75, 50)))
  }

}
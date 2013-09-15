package org.dupontmanual.image

import scala.math.Pi

import org.scalatest.FunSuite
import org.scalatest.Matchers

class PointTests extends FunSuite with Matchers {
	val p1 = Point(5,10)
	val p2 = Point(-3, 7)
	val p3 = PointPolar(2, 90.degrees)
	val p4 = PointPolar(4, (3 * scala.math.Pi).radians)
	
	test("point translation") {
	  p1.translate(2.4, 8.1) shouldEqual Point(7.4, 18.1)
	  p2.translate(0, 21) shouldEqual Point(-3, 28)
	  p1.translate(-3.5, -5.1) shouldEqual Point(1.5, 4.9)
	}
	
	test("polar point conversion") {
	  p3.toCartesian shouldEqual Point(0, 2)
	  p4.toCartesian shouldEqual Point(-4, 0)
	}
}
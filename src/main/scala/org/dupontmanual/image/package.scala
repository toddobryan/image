package org.dupontmanual

import scala.language.implicitConversions
import org.scalatest.matchers.{ HavePropertyMatcher, HavePropertyMatchResult }

/**
 * A Scala port (mostly) of the image library created in Racket by Robby Findler,
 *   with extensions created by Stephen Bloch for his book ''Picturing Programs''.
 *
 * The package object includes an implicit conversion for creating angles and
 *   a set of pictures used in the textbook.
 */
package object image {
  import AngleUnit._

  /**
   * converts a `Double` to an `[[AngleBuilder]]`.
   *
   *  `90.degrees` or `(math.Pi / 2).radians` creates an `Angle`
   *  of the given measure by first implicitly converting the
   *  `Double` to an `AngleBuilder` and then calling the `degrees`
   *  or `radians` method of that object.
   */
  implicit def doubleToAngleBuilder(d: Double) = new AngleBuilder(d)

  /** a 33x31 pixel picture of a calendar */
  lazy val Calendar = Bitmap.fromWorkspace("/calendar.png", name = Some("Calendar"))

  /** a 48x48 pixel picture of a frustrated computer operator */
  lazy val Hacker = Bitmap.fromWorkspace("/mad_hacker.png", name = Some("Hacker"))

  /** a 45x68 pixel picture of a tablet with hieroglyphics */
  lazy val Glyphs = Bitmap.fromWorkspace("/hieroglyphics.png", name = Some("Glyphs"))

  /** a 67x39 pixel picture of book with a large question mark over it */
  lazy val Book = Bitmap.fromWorkspace("/qbook.png", name = Some("Book"))

  /** a 38x39 pixel picture of a very blocky person */
  lazy val StickPerson = Bitmap.fromWorkspace("/stick-figure.png", name = Some("StickPerson"))

  /** an 85x44 pixel picture of a train car */
  lazy val TrainCar = Bitmap.fromWorkspace("/train_car.png", name = Some("TrainCar"))

  /** a 129x44 pixel picture of an old-fashioned train engine with a coal car */
  lazy val TrainEngine = Bitmap.fromWorkspace("/train_engine.png", name = Some("TrainEngine"))
  
  private[image] def repr(s: String): String = {
    if (s == null) "null"
    else s.toList.map {
      case '\0' => "\\0"
      case '\t' => "\\t"
      case '\n' => "\\n"
      case '\r' => "\\r"
      case '\"' => "\\\""
      case '\\' => "\\\\"
      case ch if (' ' <= ch && ch <= '\u007e') => ch.toString
      case ch => {
        val hex = Integer.toHexString(ch.toInt)
        "\\u%s%s".format("0" * (4 - hex.length), hex)
      }
    }.mkString("\"", "", "\"")
  }
  
  def sameBitmapAs(expectedValue: Image) =
    new HavePropertyMatcher[Image, Image] {
      def apply(image: Image) =
        HavePropertyMatchResult(
          image.sameBitmap(expectedValue),
          "sameBitmapAs",
          expectedValue,
          image
        )
    }

}
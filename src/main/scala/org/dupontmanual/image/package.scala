package org.dupontmanual

import scala.language.implicitConversions
import org.scalautils.Equality
import scalafx.Includes._
import scalafx.scene.Node
import scalafx.application.Platform
import scalafx.stage.Stage
import scalafx.stage.StageStyle
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import scalafx.geometry.Insets
import scalafx.scene.control.Button
import javafx.embed.swing.JFXPanel
import javax.swing.JFrame
import java.awt.Frame
import java.util.Arrays

/**
 * A Scala port (mostly) of the image library created in Racket by Robby Findler,
 *   with extensions created by Stephen Bloch for his book ''Picturing Programs''.
 *
 * The package object includes an implicit conversion for creating angles and
 *   a set of pictures used in the textbook.
 */
package object image {
  import AngleUnit._
  
  var masterFrame: JFrame = _
  
  def initialize() {
    masterFrame = new JFrame()
    masterFrame.add(new JFXPanel())
    masterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  }
  
  def cleanUp() {
    println("exiting platform")
    Platform.exit()
    println("disposing of frames")
    Frame.getFrames().foreach {
      _.dispose()
    }
    println("frames all disposed")
    System.exit(0);
  }
  
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
  
  def displayNode(node: Node) = {
    Platform.runLater {

      // Create dialog
      val dialogStage = new Stage(StageStyle.UTILITY) {
        outer =>
        title = "Image"
        scene = new Scene {
          root = new BorderPane {
            padding = Insets(25)
            center = node
            bottom = new Button {
              text = "OK"
              onAction = handle { outer.close() }
            }
          }
        }
      }

      // Show dialog and wait till it is closed
      dialogStage.showAndWait()
      //System.exit(0)
    }
  }
  
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
        val hex = ch.toInt
        f"\\u$hex%04x"
      }
    }.mkString("\"", "", "\"")
  }
  
  implicit val imageEquality = new Equality[Image] {
    def areEqual(img1: Image, any: Any): Boolean = any match {
      case img2: Image => img1.sameBitmapAs(img2) 
      case _ => false
    }
  }
}
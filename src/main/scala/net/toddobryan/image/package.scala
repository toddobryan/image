package net.toddobryan

import scala.language.implicitConversions
import org.scalactic.Equality
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
import _root_.java.awt.Frame
import _root_.java.util.Arrays
import scalafx.scene.control.Label
import scalafx.scene.Group
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

/**
 * A Scala port (mostly) of the image library created in Racket by Robby Findler,
 *   with extensions created by Stephen Bloch for his book ''Picturing Programs''.
 *
 * The package object includes an implicit conversion for creating angles and
 *   a set of pictures used in the textbook.
 */
package object image {
  import AngleUnit._
  import Align._
  
  var initialized = false
  
  class BackgroundApp extends javafx.application.Application {
    def start(stage: javafx.stage.Stage) {
      stage.setTitle("My JavaFX Application")
      stage.setScene(new Scene(new Group(new Label("Leave this window open until\n you're ready to quit.")), 200, 50))
      stage.show()
    }
  }
  
  def ensureInitialized[FxValue](expr: => FxValue): FxValue = {
    if (!initialized) {
      initialize()
    }
    expr
  }
  
  def initialize() {
    Platform.implicitExit = false
    new JFXPanel();
    initialized = true;
  }
  
  def cleanUp() {
    Platform.exit()
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
  lazy val Calendar: Image = Bitmap.fromWorkspace("/calendar.png", name = Some("Calendar"))

  /** a 48x48 pixel picture of a frustrated computer operator */
  lazy val Hacker: Image = Bitmap.fromWorkspace("/mad_hacker.png", name = Some("Hacker"))

  /** a 45x68 pixel picture of a tablet with hieroglyphics */
  lazy val Glyphs: Image = Bitmap.fromWorkspace("/hieroglyphics.png", name = Some("Glyphs"))

  /** a 67x39 pixel picture of book with a large question mark over it */
  lazy val Book: Image = Bitmap.fromWorkspace("/qbook.png", name = Some("Book"))

  /** a 38x39 pixel picture of a very blocky person */
  lazy val StickPerson: Image = Bitmap.fromWorkspace("/stick-figure.png", name = Some("StickPerson"))

  /** an 85x44 pixel picture of a train car */
  lazy val TrainCar: Image = Bitmap.fromWorkspace("/train_car.png", name = Some("TrainCar"))

  /** a 129x44 pixel picture of an old-fashioned train engine with a coal car */
  lazy val TrainEngine: Image = Bitmap.fromWorkspace("/train_engine.png", name = Some("TrainEngine"))
    
  private[image] def repr(s: String): String = {
    if (s == null) "null"
    else s.toList.map {
      case '\u0000' => "\\u0000"
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
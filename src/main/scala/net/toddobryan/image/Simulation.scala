package net.toddobryan.image

import scalafx.Includes._
import scalafx.animation.{ AnimationTimer, Timeline }
import scalafx.scene.layout.{ Pane, VBox }
import scalafx.scene.Scene
import scalafx.stage.{ Stage, StageStyle }
import scalafx.geometry.Pos
import scalafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent
import scalafx.animation.KeyFrame
import scalafx.util.Duration
import javafx.event.ActionEvent
import javafx.scene.input.KeyEvent
import javafx.scene.input.KeyCode
import javafx.embed.swing.JFXPanel

trait World[W <: World[_]] {
  self: W =>
  val width: Int = 200
  val height: Int = 200

  def asImage(): Image = Rectangle(Color.White, width, height)

  def afterTick(): W = this

  def afterKeyPressed(ke: String): W = this

  def afterMouseClicked(x: Double, y: Double): W = this
}

class Simulation[W <: World[W]](val startWorld: W, val fps: Int) {
  var lastTick: Option[Long] = None
  var world: W = startWorld
  val ticksBetweenFrames: Long = 1000000000L / fps

  var animationPane: Pane = _

  /** displays the simulation frame */
  def display() {
    def showFrame() {
      val handleClick: EventHandler[MouseEvent] = new EventHandler[MouseEvent]() {
        def handle(e: MouseEvent) {
          world = world.afterMouseClicked(e.sceneX, e.sceneY)
        }
      }
      val handleKeyPress: EventHandler[KeyEvent] = new EventHandler[KeyEvent]() {
        def handle(e: KeyEvent) {
          val ctrl = if (e.isControlDown() && e.getCode() != KeyCode.CONTROL) "CTRL-" else ""
          val shift = if (e.isShiftDown() && e.getCode() != KeyCode.SHIFT) "SHIFT-" else "" 
          world = world.afterKeyPressed(s"$ctrl$shift${e.getCode()}")
        }
      }
      animationPane = new Pane {
        children = startWorld.asImage.buildImage()
        prefWidth = startWorld.width
        prefHeight = startWorld.height
      }
      val myStage = new Stage(StageStyle.DECORATED) {
        outer => {
          title = "Simulation"
          scene = new Scene {
            root = new VBox {
              alignment = Pos.TopCenter
              content = List(animationPane)
            }
          }
        }
      }
      // Show dialog and wait till it is closed
      myStage.scene.value.onMouseClicked = handleClick
      myStage.scene.value.onKeyPressed = handleKeyPress
      myStage.show()
      myStage.requestFocus()
    }
    if (Platform.isFxApplicationThread) {
      showFrame()
    } else {
      Platform.runLater(showFrame())
    }
  }

  val keyFrame: KeyFrame = KeyFrame(Duration(1000 / fps), onFinished = new EventHandler[ActionEvent]() {
    def handle(e: ActionEvent) {
      world = world.afterTick()
      animationPane.children = world.asImage.buildImage()
    }
  })
  
  def run() {
    new JFXPanel()
    this.display()
    new Timeline(fps, List(keyFrame)) {
      cycleCount = Timeline.Indefinite
    }.play()
  }
}

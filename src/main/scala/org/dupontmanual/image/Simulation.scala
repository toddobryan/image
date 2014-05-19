package org.dupontmanual.image

import scala.swing.MainFrame
import java.awt.event.ActionListener
import javax.swing.Timer
import scala.swing.Panel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import scala.swing.event.ActionEvent
import java.awt.event.MouseListener
import java.awt.event.KeyListener
import scala.swing.event.MouseEvent
import scala.swing.event.MouseClicked
import scala.swing.event.KeyPressed
import java.awt.event.KeyEvent
import scala.swing.SimpleSwingApplication
import scala.swing.BorderPanel

class Simulation[W <: World[W]](val startWorld: W, val fps: Int) extends SimpleSwingApplication {
  var world: W = startWorld
  val ticksBetweenFrames: Int = 1000 / fps
  val timer: ScalaTimer = new ScalaTimer(ticksBetweenFrames)
  
  def getWorld: W = world
  
  val panel: Panel = new Panel {
    override def paint(g: Graphics2D) {
      getWorld.asImage().render(g)
    }
  }
  
  def top = new MainFrame {
	title = "Simulation"
	preferredSize = new Dimension(getWorld.width, getWorld.height)
	contents = panel
	listenTo(timer, panel.mouse.clicks, panel.keys)
	reactions += {
	  case evt: TimerEvent =>
	    world = world.afterTick()
	    this.repaint()
	  case evt: MouseClicked =>
	    world = world.afterMouseClicked(evt.point.x, evt.point.y)
	    this.repaint()
	  case evt: KeyPressed =>
	    world = world.afterKeyPressed(KeyEvent.getKeyText(evt.peer.getKeyCode()))
	    this.repaint()
	}
	panel.requestFocusInWindow()
    timer.start()
  }

  def run() {
    main(Array())
  }
}
package org.dupontmanual.image

import scala.swing.Frame
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

class Simulation[W <: World[W]](val startWorld: W, val fps: Int) extends Frame {
  var world: W = startWorld
  val ticksBetweenFrames: Int = 1000 / fps
  val timer: ScalaTimer = new ScalaTimer(ticksBetweenFrames)
  
  def getWorld: W = world
  
  val panel: Panel = new Panel() {
    def paintComponent(g: Graphics) {
      getWorld.asImage().render(g.asInstanceOf[Graphics2D])
    }
  }
  
  this.listenTo(timer, panel.mouse.clicks, panel.keys)
  
  this.reactions += {
    case evt: TimerEvent =>
      world = world.afterTick()
      panel.repaint()
    case evt: MouseClicked =>
      world = world.afterMouseClicked(evt.point.x, evt.point.y)
      panel.repaint()
    case evt: KeyPressed =>
      world = world.afterKeyPressed(KeyEvent.getKeyText(evt.peer.getKeyCode()))
      panel.repaint()
  }

  def run() {
    preferredSize = new Dimension(500, 500)
    contents = panel
    pack()
    visible = true
    timer.start(); //This starts the animation.
  }

  def actionPerformed(evt: ActionEvent) {
    if (evt.source == timer) {
      world = world.afterTick()
      panel.repaint()
    }
  }
}
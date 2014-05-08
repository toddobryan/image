package org.dupontmanual.image

import scala.swing.Frame
import java.awt.event.ActionListener
import javax.swing.Timer
import scala.swing.Panel
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import scala.swing.event.ActionEvent

class Simulation[W <: World[W]](val startWorld: W, val fps: Int) extends Frame with ActionListener {
  var world: W = startWorld
  val ticksBetweenFrames: Int = 1000 / fps
  val timer: Timer = new Timer(ticksBetweenFrames, this)

  def run() {
    val panel: Panel = new Panel() {
      override def paintComponent(g: Graphics) {
        world.asImage().render(g.asInstanceOf[Graphics2D])
      }
    }
    preferredSize = new Dimension(500, 500)
    contents = panel
    pack()
    visible = true
    timer.start(); //This starts the animation.
  }

  def actionPerformed(evt: ActionEvent) {
    

}
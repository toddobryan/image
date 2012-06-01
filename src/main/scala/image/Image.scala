package image

import java.awt.{Graphics2D, RenderingHints}
import java.awt.image.BufferedImage
import javax.swing.ImageIcon
import scala.swing.Dialog
import javax.imageio.ImageIO
import java.io.File

abstract class Image {  
  lazy val img: BufferedImage = new BufferedImage(bounds.width + 1, bounds.height + 1, BufferedImage.TYPE_INT_ARGB)
  
  def display() {
    val g2 = img.getGraphics.asInstanceOf[Graphics2D]
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    render(g2)
    Dialog.showMessage(message = null, icon = new ImageIcon(img))
  }
  
  def save(filename: String) {
    ImageIO.write(this.img, "png", new File(filename))
  }
  
  def bounds: Bounds
  def render(g2: Graphics2D)
}


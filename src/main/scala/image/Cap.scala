package image

import java.awt.BasicStroke

/**
 * represents the options for decorating the ends of unclosed
 * subpaths and dash segments in a shape consisting of many lines.
 */
private[image] sealed abstract class Cap {
  private[image] def toBsCap: Int
}

/**
 * companion object with the three decoration types
 */
object Cap {
  /** ends line segments with no decoration */
  object None extends Cap {
    private[image] def toBsCap = BasicStroke.CAP_BUTT
    override def toString = "Cap.None"
  }
  
  /** ends segments with a round decoration half the radius of the `[[Pen]]` */
  object Round extends Cap {
    private[image] def toBsCap = BasicStroke.CAP_ROUND
    override def toString = "Cap.Round"
  }
  
  /** ends segments with a square decoration half the radius of the `[[Pen]]` */ 
  object Square extends Cap {
    private[image] def toBsCap = BasicStroke.CAP_SQUARE
    override def toString = "Cap.Square"
  }
}
package org.dupontmanual.image
import java.awt.BasicStroke

/** parent class for style to use when lines are joined */
sealed abstract class Join private[image]() {
  private[image] def toBsJoin: Int
}

/** companion class containing the three styles used for joining lines */
object Join {
  /** this style "cuts the corner" where two lines meet */
  object Bevel extends Join {
    private[image] def toBsJoin = BasicStroke.JOIN_BEVEL
    /** returns `"Join.Bevel"` */
    override def toString = "Join.Bevel"
  }
  /** this style extends lines until they meet at a point */
  object Miter extends Join {
    private[image] def toBsJoin = BasicStroke.JOIN_MITER
    /** returns `"Join.Miter"` */
    override def toString = "Join.Miter"
  }
  /** this style rounds the corner where two lines meet */
  object Round extends Join {
    private[image] def toBsJoin = BasicStroke.JOIN_ROUND
    /** returns `"Join.Round"` */
    override def toString = "Join.Round"
  }
}

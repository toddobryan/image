package image
import java.awt.BasicStroke

sealed abstract class Join {
  def toBsJoin: Int
}

object Join {
  object Bevel extends Join {
    def toBsJoin = BasicStroke.JOIN_BEVEL
  }
  object Miter extends Join {
    def toBsJoin = BasicStroke.JOIN_MITER
  }
  object Round extends Join {
    def toBsJoin = BasicStroke.JOIN_ROUND
  }
}

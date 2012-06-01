package image

import java.awt.BasicStroke

sealed abstract class Cap {
  def toBsCap: Int
}

object Cap {
  object None extends Cap {
    def toBsCap = BasicStroke.CAP_BUTT
  }
  object Round extends Cap {
    def toBsCap = BasicStroke.CAP_ROUND
  }
  object Square extends Cap {
    def toBsCap = BasicStroke.CAP_SQUARE
  }
}
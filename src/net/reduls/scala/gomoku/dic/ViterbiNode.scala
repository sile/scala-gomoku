package net.reduls.scala.gomoku.dic

case class ViterbiNode(val start:Int, val end:Int, var cost:Int, val posId:Short) {
  var prev:ViterbiNode = null
}

object ViterbiNode {
  def makeBOSEOS: ViterbiNode = ViterbiNode(0, 0, 0, 0)
}

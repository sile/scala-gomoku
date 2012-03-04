package net.reduls.scala.gomoku.dic

final class ViterbiNode(val start:Int, val end:Int, var cost:Int, val posId:Short, val isSpace:Boolean) {
  var prev:ViterbiNode = null
}

object ViterbiNode {
  def makeBOSEOS: ViterbiNode = new ViterbiNode(0, 0, 0, 0, false)
}

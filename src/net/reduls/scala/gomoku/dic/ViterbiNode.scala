package net.reduls.scala.gomoku.dic

final class ViterbiNode(val start:Int,
                        val end:Int,
                        wordCost:Short,
                        val posId:Short,
                        val isSpace:Boolean) {
  var cost:Int = wordCost
  var prev:ViterbiNode = null
}

object ViterbiNode {
  def makeBOSEOS: ViterbiNode = new ViterbiNode(0, 0, 0, 0, false)
}

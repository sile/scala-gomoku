package net.reduls.scala.gomoku.dic

final class ViterbiNode(val start:Int,
                        val length:Short,
                        wordCost:Short,
                        val posId:Short,
                        val isSpace:Boolean) {
  var cost:Int = wordCost
  var prev:List[ViterbiNode] = Nil
}

object ViterbiNode {
  def makeBOSEOS: ViterbiNode = new ViterbiNode(0, 0, 0, 0, false)
}

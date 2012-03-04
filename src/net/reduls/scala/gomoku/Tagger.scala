package net.reduls.scala.gomoku

import scala.collection.mutable.ArrayBuffer
import net.reduls.scala.gomoku.dic.Matrix
import net.reduls.scala.gomoku.dic.WordDic
import net.reduls.scala.gomoku.dic.ViterbiNode
import net.reduls.scala.gomoku.dic.PartsOfSpeech

object Tagger {
  private val BOS_NODES = ArrayBuffer[ViterbiNode](ViterbiNode.makeBOSEOS)

  def parse(text:String): List[Morpheme] = 
    mapViterbiNode(parseImpl(text)) {
      vn => val surface = text.substring(vn.start, vn.end)
            val feature = PartsOfSpeech(vn.posId)
            Morpheme(surface, feature, vn.start)
    }

  def wakati(text:String): List[String] = 
    mapViterbiNode(parseImpl(text)) {
      vn => text.substring(vn.start, vn.end)
    }
        
  private def parseImpl(text:String): ViterbiNode = {
    val nodesAry = Array.tabulate(text.length+1){case 0 => BOS_NODES
                                                 case _ => ArrayBuffer[ViterbiNode]()}
    for(i <- 0 until text.length) {
      val prevs = nodesAry(i)
      if(prevs.isEmpty == false)
        WordDic.search(text, i) {
          vn => if(vn.isSpace) nodesAry(vn.end) ++= prevs
                else           nodesAry(vn.end) += setMinCostNode(vn, prevs)          
        }
      nodesAry(i) = null
    }
    
    setMinCostNode(ViterbiNode.makeBOSEOS, nodesAry(text.length)).prev
  }

  private def setMinCostNode(vn:ViterbiNode, prevs:ArrayBuffer[ViterbiNode]): ViterbiNode = {
    var minCost = Integer.MAX_VALUE
    for(p <- prevs) {
      val cost = p.cost + Matrix.linkCost(p.posId, vn.posId)
      if(cost < minCost) {
        minCost = cost
        vn.prev = p
      }
    }
    vn.cost += minCost
    vn
  }

  private def mapViterbiNode[T](end:ViterbiNode)(fn:ViterbiNode=>T): List[T] = {
    def recur(cur:ViterbiNode, acc:List[T]): List[T] =
      if(cur.prev == null) acc
      else                 recur(cur.prev, fn(cur) :: acc)
    recur(end, Nil)
  }
}

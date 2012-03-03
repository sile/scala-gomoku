package net.reduls.scala.gomoku

import net.reduls.scala.gomoku.dic.ViterbiNode
import net.reduls.scala.gomoku.dic.WordDic
import net.reduls.scala.gomoku.dic.Unknown
import net.reduls.scala.gomoku.dic.Matrix
import net.reduls.scala.gomoku.dic.PartsOfSpeech

object Tagger {
  private final val BOS_NODES = List[ViterbiNode](ViterbiNode.makeBOSEOS)

  def parse(text:String): List[Morpheme] = 
    mapViterbiNode(parseImpl(text)) {
      vn =>
        val surface = text.substring(vn.start, vn.start+vn.length)
        val feature = PartsOfSpeech.get(vn.posId)
        new Morpheme(surface, feature, vn.start)
    }

  def wakati(text:String): List[String] = 
    mapViterbiNode(parseImpl(text)) {
      vn => text.substring(vn.start, vn.start+vn.length)
    }
        
  // TODO
  private def mapViterbiNode[T](end:ViterbiNode)(fn:ViterbiNode=>T): List[T] = {
    def recur(cur:ViterbiNode, acc:List[T]): List[T] =
      if(cur.prev == null)
        acc
      else
        recur(cur.prev, fn(cur) :: acc)
    recur(end, Nil)
  }

  private def parseImpl(text:String): ViterbiNode = {
    val nodesAry = Array.fill[List[ViterbiNode]](text.length+1)(Nil)
    nodesAry(0) = BOS_NODES

    for(i <- 0 until text.length) {
      val prevs = nodesAry(i)
      if(prevs != Nil) {
        nodesAry(i) = Nil // release reference for GC
          
        var noMatch = true
        val fn = (vn:ViterbiNode) => {
          noMatch = false
          val end = i+vn.length
          if(vn.isSpace) nodesAry(end) = nodesAry(end) ++ prevs
          else           nodesAry(end) = nodesAry(end) ++ List(setMinCostNode(vn, prevs))
//          else           nodesAry(end) = setMinCostNode(vn, prevs) :: nodesAry(end)
        }
        
        WordDic.search(text, i, fn)
        Unknown.search(text, i, noMatch, fn)
      }
    }
    setMinCostNode(ViterbiNode.makeBOSEOS, nodesAry(text.length)).prev
  }

  private def setMinCostNode(vn:ViterbiNode, prevs:List[ViterbiNode]): ViterbiNode = {
    var minPrev:ViterbiNode = null
    var minCost = Integer.MAX_VALUE
    
    for(p <- prevs) {
      val cost = p.cost + Matrix.linkCost(p.posId, vn.posId)
      if(cost < minCost) {
        minCost = cost
        minPrev = p
      }
    }
    vn.cost += minCost
    vn.prev = minPrev
    vn
  }
}

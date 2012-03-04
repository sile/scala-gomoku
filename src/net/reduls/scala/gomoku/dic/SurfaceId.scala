package net.reduls.scala.gomoku.dic

object SurfaceId {
  private val nodes =
    Util.withDictionaryDataWithCount("surface-id.bin") {
      (in, nodeCount) => Array.tabulate(nodeCount){_ => in.readLong}
    }

  private val idOffset = 
    Util.withDictionaryDataWithCount("category.bin") {
      (_, idOffset) => idOffset
    }

  def eachCommonPrefix(text:String, start:Int, fn: WordDic.Callback): Boolean = {
    def recur(node:Int, i:Int, id:Int, matchCount:Int): Boolean = {
      val idadj = 
        if(isTerminal(node)) {
          WordDic.eachViterbiNode(id, start, i, false, fn)
          1
        } else
          0

      if(i == text.length)
        return matchCount+idadj > 0

      val arc = Char.code(text(i))
      val next = base(node) + arc
      if(chck(next) != arc)
        return matchCount+idadj > 0
      recur(next, i+1, id+siblingTotal(next)+idadj, matchCount+idadj)
    }
    recur(0, start, idOffset, 0)
  }
  
  private def chck(node:Int): Char = ((nodes(node)>>24)&0xFFFF).toChar
  private def base(node:Int): Int = (nodes(node)&0xFFFFFF).toInt
  private def isTerminal(node:Int): Boolean = ((nodes(node)>>40)&0x1) == 0x1
  private def siblingTotal(node:Int): Int = (nodes(node)>>41).toInt
}

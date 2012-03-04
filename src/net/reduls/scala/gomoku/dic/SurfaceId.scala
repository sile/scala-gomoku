package net.reduls.scala.gomoku.dic

object SurfaceId {
  private val nodes =
    Util.withDictionaryDataWithCount("surface-id.bin") {
      (in, nodeCount) => Array.tabulate(nodeCount){_ => in.readLong}
    }

  private val idOffset = 
    util.withDictionaryDataWithCount("category.bin") {
      (_, idOffset) => idOffset
    }

  def eachCommonPrefix(text:String, start:Int, fn: WordDic.Callback): Unit = {
    def recur(node:Int, i:Int, id:Int): Unit = {
      val idadj = 
        if(isTerminal(node)) {
          WordDic.eachViterbiNode(id, start, (i-start).toShort, false, fn)
          1
        } else
          0

      if(i < text.length) {
        val arc = Char.code(text(i))
        val next = base(node) + arc
        if(chck(next) == arc)
          recur(next, i+1, id+siblingTotal(next)+idadj)
      }
    }
    recur(0, start, idOffset)
  }
  
  private def chck(node:Int): Char = ((nodes(node)>>24)&0xFFFF).toChar
  private def base(node:Int): Int = (nodes(node)&0xFFFFFF).toInt
  private def isTerminal(node:Int): Boolean = ((nodes(node)>>40)&0x1) == 0x1
  private def siblingTotal(node:Int): Int = (nodes(node)>>41).toInt
}

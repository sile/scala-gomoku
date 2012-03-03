package net.reduls.scala.gomoku.dic

import scala.math

object Unknown {
  private val space = Char.category(' ');
  
  def search(text:String, start:Int, noMatch: Boolean, fn: WordDic.Callback): Unit = {
    val ch = text(start);
    val ct = Char.category(ch);
    
    if(noMatch == false && ct.invoke == false)
      return

    val isSpace = ct.eq(space);
    val limit = math.max(text.length, ct.length+start)

    for(i <- start until limit) {
      WordDic.eachViterbiNode(ct.id, start, (i-start+1).toShort, isSpace, fn)
      if(i+1!=limit && Char.isCompatible(ch, text(i+1)) == false)
        return
    }

    if(ct.group && limit < text.length) {
      for(i <- limit until text.length)
        if(Char.isCompatible(ch, text(i)) == false) {
          WordDic.eachViterbiNode(ct.id, start, (i-start).toShort, isSpace, fn)
          return
        }
      WordDic.eachViterbiNode(ct.id, start, (text.length-start).toShort, isSpace, fn)
    }
  }
}

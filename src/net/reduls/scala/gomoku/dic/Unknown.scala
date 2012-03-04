package net.reduls.scala.gomoku.dic

import scala.math

object Unknown {
  private val space = Char.category(' ');
  
  def search(text:String, start:Int, matched: Boolean, fn: WordDic.Callback) {
    val ch = text(start)
    val ct = Char.category(ch)
    
    if(matched == false || ct.invoke == true) {
      val isSpace = ct.eq(space);
      val limit = math.min(text.length, start+ct.length)
      
      for(i <- start until limit) {
        WordDic.eachViterbiNode(ct.id, start, i+1, isSpace, fn)
        if(i+1 != limit && Char.isCompatible(ch, text(i+1)) == false)
            return
      }
        
      if(ct.group && limit < text.length)
        for(i <- limit to text.length)
            if(i == text.length || Char.isCompatible(ch, text(i)) == false) {
              WordDic.eachViterbiNode(ct.id, start, i, isSpace, fn)
              return
            }
    }
  }
}

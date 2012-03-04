package net.reduls.scala.gomoku.dic

object WordDic {
  type Callback = ViterbiNode => Unit
  def search(text:String, start:Int, fn: Callback): Unit =
    SurfaceId.eachCommonPrefix(text, start, fn)
    
  def eachViterbiNode(surfaceId:Int, start:Int, length:Short, isSpace:Boolean, fn: Callback): Unit = {
    for(m <- Morpheme.getMorphemes(surfaceId)) 
      fn(new ViterbiNode(start, start+length, Morpheme.cost(m), Morpheme.posId(m), isSpace))
  }
}

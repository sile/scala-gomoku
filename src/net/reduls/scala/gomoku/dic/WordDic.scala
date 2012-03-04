package net.reduls.scala.gomoku.dic

object WordDic {
  type Callback = ViterbiNode => Unit
  
  def search(text:String, start:Int, fn: Callback) {
    SurfaceId.eachCommonPrefix(text, start, fn)
  }
    
  def eachViterbiNode(surfaceId:Int, start:Int, end:Int, isSpace:Boolean, fn: Callback) {
    for(m <- Morpheme.getMorphemes(surfaceId)) 
      fn(ViterbiNode(start, end, Morpheme.cost(m), Morpheme.posId(m), isSpace))
  }
}

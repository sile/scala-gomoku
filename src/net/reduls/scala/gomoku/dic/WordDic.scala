package net.reduls.scala.gomoku.dic

object WordDic {
  type Callback = (Boolean, ViterbiNode) => Unit
  
  def search(text:String, start:Int)(fn: Callback) {
    val matched = SurfaceId.eachCommonPrefix(text, start, fn)
    Unknown.search(text, start, matched, fn)
  }
    
  def eachViterbiNode(surfaceId:Int, start:Int, end:Int, isSpace:Boolean, fn: Callback) {
    for(m <- Morpheme.getMorphemes(surfaceId)) 
      fn(isSpace, ViterbiNode(start, end, Morpheme.cost(m), Morpheme.posId(m)))
  }
}

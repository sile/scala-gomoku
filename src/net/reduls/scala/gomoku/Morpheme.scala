package net.reduls.scala.gomoku

case class Morpheme(val surface:String, val feature:String, val start:Int) {
  override def toString: String = surface+"@"+feature
}

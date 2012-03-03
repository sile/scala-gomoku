package net.reduls.scala.gomoku.util

import java.io.InputStream
import java.io.BufferedInputStream
import java.io.DataInputStream

object Misc {
  def openDictionaryData(filename:String): DataInputStream = {
    new DataInputStream(
      new BufferedInputStream(
        getClass.getResourceAsStream("/net/reduls/scala/gomoku/data/"+filename)))
  }

  def withDictionayData[T](filename:String)(body: (DataInputStream,Int) => T): T = {
    val in = openDictionaryData(filename)
    try
      body(in, in.readInt)
    finally
      in.close()
  }

  def withDictionayData[T](filename1:String, filename2:String)(body: (DataInputStream,Int,DataInputStream,Int) => T): T =
    withDictionayData(filename1) {
      (in1,count1) => withDictionayData(filename2) {
        (in2,count2) => body(in1, count1, in2, count2)
      }
    }
}

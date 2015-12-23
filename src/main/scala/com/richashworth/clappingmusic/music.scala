package com.richashworth.clappingmusic

/**
  * Created by Rich Ashworth on 22/12/2015.
  */
sealed abstract class Beat
case object Rest extends Beat
case object Note extends Beat

class Pattern(val beats: Seq[Beat]) {
  def this(notation: String) = {
    this(notation.map(_.toUpper match {
      case 'X' => Note
      case _   => Rest
    }))
  }

  def length = beats.length

  override def toString(): String = {
    beats.map(_ match {
      case Note => "X"
      case Rest => "_"
    }) mkString " "
  }
}


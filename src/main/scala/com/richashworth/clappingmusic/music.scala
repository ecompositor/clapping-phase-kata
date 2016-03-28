package com.richashworth.clappingmusic

/**
  * Created by Rich Ashworth on 22/12/2015.
  */
sealed abstract class Beat
case object Rest extends Beat
case object Clap extends Beat

class Phrase(val beats: Seq[Beat]) {
  def this(notation: String) = {
    this(notation.map(_.toUpper match {
      case 'X' ⇒ Clap
      case _   ⇒ Rest
    }))
  }

  def length = beats.length

  override def toString(): String = {
    beats.map(_ match {
      case Clap ⇒ "X"
      case Rest ⇒ "_" }) mkString " "
  }
}


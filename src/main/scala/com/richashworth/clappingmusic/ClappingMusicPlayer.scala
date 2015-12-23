package com.richashworth.clappingmusic

import scala.annotation.tailrec
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.async

/**
  * Created by Rich Ashworth on 22/12/2015.
  */
object ClappingMusicPlayer extends App {

  val musician = new MidiMusician(9)
  val original: Pattern = new Pattern("XXX XX X XX ")

  val clappingMusic = Composer.composeTwoPartPhaseMusic(original)
  play(clappingMusic)

  def play(sections: Stream[(Pattern, Pattern)]) {
    playStream(sections, 0)

    @tailrec
    def playStream(sections: Stream[(Pattern, Pattern)], acc: Int): Unit = sections match {
      case Stream.Empty                 => musician.stop
      case (a, b) #:: remainingSections => {
        println(s"phase $acc:")
        (a, b).productIterator.foreach(println)
        (1 to 8).foreach(i => {
          print(s"$i ")
          async {
            musician.playPattern(pitch = 42, pattern = a)
          }
          musician.playPattern(pitch = 35, pattern = b)
        })
        println(System.lineSeparator() + "=" * a.toString.length)
        playStream(remainingSections, acc + 1)
      }
    }
  }

}

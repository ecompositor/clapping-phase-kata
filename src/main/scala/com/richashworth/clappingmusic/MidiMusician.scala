package com.richashworth.clappingmusic

import javax.sound.midi.MidiSystem

import scala.annotation.tailrec
import scala.async.Async.async
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Rich Ashworth on 22/12/2015.
  */
class MidiPlayer(midiChannel: Int) {

  val synthesizer = MidiSystem.getSynthesizer()
  synthesizer.open()

  val channel = synthesizer.getChannels()(midiChannel)

  @tailrec final def play(sections: Stream[(Phrase, Phrase)], acc: Int = 0) {
    sections match {
      case Stream.Empty  ⇒ stop
      case (a, b) #:: xs ⇒
        println(s"phase $acc:")
        (a, b).productIterator.foreach(println)
        (1 to 8).foreach(i ⇒ {
          print(s"$i ")
          async {
            playPattern(pitch = 42, pattern = a)
          }
          playPattern(pitch = 35, pattern = b)
        })
        println(System.lineSeparator() + "=" * a.toString.length)
        play(xs, acc + 1)
    }
  }

  def playPattern(pitch: Int, velocity: Int = 100,
                  duration: Int = 160, pattern: Phrase) {
    pattern.beats.foreach(_ match {
      case Rest ⇒ Thread.sleep(duration)
      case Clap ⇒ {
        channel.noteOn(pitch, velocity)
        Thread.sleep(duration)
        channel.noteOff(pitch)
      }
    })
  }

  def stop {
    channel.allNotesOff()
    synthesizer.close
  }
}

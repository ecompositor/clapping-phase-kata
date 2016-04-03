package com.richashworth.clappingmusic

import javax.sound.midi.MidiChannel

import akka.actor._

/**
  * Created by Rich Ashworth on 29/03/2016.
  */
class Musician(channel: MidiChannel, pitch: Int) extends Actor {

  private val beatLength = 180

  def receive = {
    case Ping ⇒ sender ! Ping
    case Rest ⇒ Thread.sleep(beatLength)
    case Clap ⇒ {
      channel.noteOn(pitch, 100)
      Thread.sleep(beatLength)
      channel.noteOff(pitch)
    }
  }
}

case object Ping

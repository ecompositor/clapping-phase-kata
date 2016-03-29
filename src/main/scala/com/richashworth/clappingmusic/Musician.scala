package com.richashworth.clappingmusic

import javax.sound.midi.{MidiChannel, MidiSystem}

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.duration._

/**
  * Created by rich on 29/03/2016.
  */
class Musician(channel: MidiChannel, pitch: Int) extends Actor {

  private val beatLength = 160

  def receive = {
    case Rest ⇒ Thread.sleep(beatLength)
    case Clap ⇒ {
      channel.noteOn(pitch, 100)
      Thread.sleep(beatLength)
      channel.noteOff(pitch)
    }
    case Ping => sender ! Ping
  }
}

object Main extends App {
  val system = ActorSystem("HelloSystem")
  val synthesizer = MidiSystem.getSynthesizer()
  synthesizer.open()
  val channel = synthesizer.getChannels()(10)
  val midiActorOne = system.actorOf(Props(new Musician(channel, 42)), name = "A")
  val midiActorTwo = system.actorOf(Props(new Musician(channel, 35)), name = "B")
  (1 to 20).foreach(i => {
    midiActorOne ! Clap
    midiActorTwo ! Clap
  })
  implicit val timeout = Timeout(5 seconds)
  val future = midiActorOne ? Ping
  val result = Await.ready(future, Duration.Inf)
  system.terminate()
}

case object Ping
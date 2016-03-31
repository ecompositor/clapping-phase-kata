package com.richashworth.clappingmusic

import javax.sound.midi.MidiSystem

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by Rich Ashworth on 31/03/2016.
  */
object Main extends App {
  val synthesizer = MidiSystem.getSynthesizer()
  synthesizer.open()

  val channel = synthesizer.getChannels()(1)

  val system       = ActorSystem("MusicianSystem")
  val midiActorOne = system.actorOf(Props(new Musician(channel, 60)), name = "A")
  val midiActorTwo = system.actorOf(Props(new Musician(channel, 67)), name = "B")

  val maxPlayingTime   = 1 hour
  implicit val timeout = Timeout(maxPlayingTime)

  val clappingMusic    = Composer.composeTwoPartPhaseMusic(new Phrase("XXX XX X XX "))
  val phaseRepetitions = 4

  clappingMusic.foreach(duet ⇒
    (1 until phaseRepetitions).foreach(_ ⇒
      (0 until duet._1.length).foreach(i ⇒ {
        midiActorOne ! duet._1.beats(i)
        midiActorTwo ! duet._2.beats(i)
      })
    )
  )

  val futures = Future sequence Seq(midiActorOne ? Ping, midiActorTwo ? Ping)
  Await.ready(futures, maxPlayingTime)

  system.terminate()
}

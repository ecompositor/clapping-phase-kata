package com.richashworth.clappingmusic

import javax.sound.midi.MidiSystem

/**
  * Created by Rich Ashworth on 22/12/2015.
  */
class MidiMusician(midiChannel: Int) {

  val synthesizer = MidiSystem.getSynthesizer()
  synthesizer.open()

  val channel = synthesizer.getChannels()(midiChannel)

  def playPattern(pitch: Int, velocity: Int = 100,
                  duration: Int = 160, pattern: Pattern) {
    pattern.beats.foreach(_ match {
      case Rest => Thread.sleep(duration)
      case Note => {
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

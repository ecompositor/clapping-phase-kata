package com.richashworth.clappingmusic

object ClappingMusic extends App {
  val midiPlayer = new MidiPlayer(9)
  val originalPattern: Pattern = new Pattern("XXX XX X XX ")
  midiPlayer.play(Composer.composeTwoPartPhaseMusic(originalPattern))
}

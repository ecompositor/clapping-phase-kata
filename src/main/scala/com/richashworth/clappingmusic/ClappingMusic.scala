package com.richashworth.clappingmusic

object ClappingMusic extends App {
  val midiPlayer = new MIDIPlayer(9)
  val originalPattern: Phrase = new Phrase("XXX XX X XX ")
  midiPlayer.play(Composer.composeTwoPartPhaseMusic(originalPattern))
}

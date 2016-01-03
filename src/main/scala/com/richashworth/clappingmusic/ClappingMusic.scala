package com.richashworth.clappingmusic

object ClappingMusic extends App {
  val musician = new MidiMusician(9)
  val originalPattern: Pattern = new Pattern("XXX XX X XX ")
  musician.play(Composer.composeTwoPartPhaseMusic(originalPattern))
}

package com.richashworth.clappingmusic

/**
  * Created by Rich Ashworth on 23/12/2015.
  */
object Composer {
  def getPhase(original: Phrase, degrees: Int = 1): Phrase = {
    new Phrase(original.beats.drop(degrees) ++ original.beats.take(degrees))
  }

  def composeTwoPartPhaseMusic(original: Phrase): Stream[(Phrase, Phrase)] = {
    val partOne = Stream.continually(original).take(1 + original.length)
    val partTwo = Stream.from(0).map(getPhase(original, _)).take(1 + original.length)
    partOne.zip(partTwo)
  }
}

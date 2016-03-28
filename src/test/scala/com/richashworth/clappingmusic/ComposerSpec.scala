package com.richashworth.clappingmusic

import org.scalatest.FlatSpec

/**
  * Created by rich on 23/12/2015.
  */
class ComposerSpec extends FlatSpec {

  "The Composer object" should "correctly shift a pattern by one beat" in {
    val originalPattern = new Phrase("X_")
    val phaseOne = Composer.getPhase(originalPattern, 1)
    assert(phaseOne === new Phrase("_X"))
  }

  it should "preserve a pattern shifted by zero beats" in {
    val originalPattern = new Phrase("X_")
    val phaseZero = Composer.getPhase(originalPattern, 0)
    assert(phaseZero === originalPattern)
  }

  it should "correctly shift a six-beat pattern by four beats" in {
    val originalPattern = new Phrase("X_XX__")
    val phaseFour = Composer.getPhase(originalPattern, 4)
    assert(phaseFour === new Phrase("__X_XX"))
  }

  it should "preserve a pattern shifted by the pattern's length" in {
    val originalPattern = new Phrase("X_XX__")
    val fullCircle = Composer.getPhase(originalPattern, originalPattern.length)
    assert(fullCircle === originalPattern)
  }
}

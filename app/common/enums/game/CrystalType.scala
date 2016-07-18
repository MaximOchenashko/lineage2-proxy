package common.enums.game

import common.enums.base.{FileDataBased, FileDataEnumHolder}

/**
  * @author iRevThis
  */
trait CrystalType extends FileDataBased

object CrystalType extends FileDataEnumHolder[CrystalType] {
  override def values: Seq[CrystalType] = Seq(None, D, C, B, A, S, S80, S84, CrystalFree, Event)

  case object None extends CrystalType {
    override def name: String = "None"

    override def fileValue: String = "none"

    override def code: Int = 0
  }

  case object D extends CrystalType {
    override def name: String = "D"

    override def fileValue: String = "d"

    override def code: Int = 1
  }

  case object C extends CrystalType {
    override def name: String = "C"

    override def fileValue: String = "c"

    override def code: Int = 2
  }

  case object B extends CrystalType {
    override def name: String = "B"

    override def fileValue: String = "b"

    override def code: Int = 3
  }

  case object A extends CrystalType {
    override def name: String = "A"

    override def fileValue: String = "a"

    override def code: Int = 4
  }

  case object S extends CrystalType {
    override def name: String = "S"

    override def fileValue: String = "s"

    override def code: Int = 5
  }

  case object S80 extends CrystalType {
    override def name: String = "S80"

    override def fileValue: String = "s80"

    override def code: Int = 6
  }

  case object S84 extends CrystalType {
    override def name: String = "S84"

    override def fileValue: String = "s84"

    override def code: Int = 7
  }

  case object CrystalFree extends CrystalType {
    override def name: String = "CrystalFree"

    override def fileValue: String = "crystal_free"

    override def code: Int = 8
  }

  case object Event extends CrystalType {
    override def name: String = "Event"

    override def fileValue: String = "event"

    override def code: Int = 9
  }

}
   
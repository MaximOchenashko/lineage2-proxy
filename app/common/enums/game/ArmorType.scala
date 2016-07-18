package common.enums.game

import common.enums.base.{FileDataBased, FileDataEnumHolder}

/**
  * @author iRevThis
  */
trait ArmorType extends FileDataBased

object ArmorType extends FileDataEnumHolder[ArmorType] {
  override def values: Seq[ArmorType] = Seq(None, Light, Heavy, Magic, Sigil)


  case object None extends ArmorType {
    override def name: String = "None"

    override def fileValue: String = "none"

    override def code: Int = 0
  }

  case object Light extends ArmorType {
    override def name: String = "Light"

    override def fileValue: String = "light"

    override def code: Int = 1
  }

  case object Heavy extends ArmorType {
    override def name: String = "Heavy"

    override def fileValue: String = "heavy"

    override def code: Int = 2
  }

  case object Magic extends ArmorType {
    override def name: String = "Magic"

    override def fileValue: String = "magic"

    override def code: Int = 3
  }

  case object Sigil extends ArmorType {
    override def name: String = "Sigil"

    override def fileValue: String = "sigil"

    override def code: Int = 4
  }

}
   
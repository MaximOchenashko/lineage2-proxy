package common.enums.game

import common.enums.base.{EnumHolder, EnumLike}

/**
  * @author iRevThis
  */
trait ElementalAttributes extends EnumLike {
  def value: Int
}

object ElementalAttributes extends EnumHolder[ElementalAttributes] {
  override def values: Seq[ElementalAttributes] = Seq(None, Fire, Water, Wind, Earth, Holy, Dark)

  case object None extends ElementalAttributes {
    override def name: String = "None"

    override def value: Int = -1

    override def code: Int = 0
  }

  case object Fire extends ElementalAttributes {
    override def name: String = "Fire"

    override def value: Int = 0

    override def code: Int = 1
  }

  case object Water extends ElementalAttributes {
    override def name: String = "Water"

    override def value: Int = 1

    override def code: Int = 2
  }

  case object Wind extends ElementalAttributes {
    override def name: String = "Wind"

    override def value: Int = 2

    override def code: Int = 3
  }

  case object Earth extends ElementalAttributes {
    override def name: String = "Earth"

    override def value: Int = 3

    override def code: Int = 4
  }

  case object Holy extends ElementalAttributes {
    override def name: String = "Holy"

    override def value: Int = 4

    override def code: Int = 5
  }

  case object Dark extends ElementalAttributes {
    override def name: String = "Dark"

    override def value: Int = 5

    override def code: Int = 6
  }

}

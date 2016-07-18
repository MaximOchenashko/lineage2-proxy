package common.enums.game

import common.enums.base.{FileDataBased, FileDataEnumHolder}

/**
  * @author iRevThis
  */
trait WeaponType extends FileDataBased

object WeaponType extends FileDataEnumHolder[WeaponType] {
  override def values: Seq[WeaponType] = Seq(Sword, Blunt, Dagger, Bow, Pole, None, Dual, Etc, Fist, Dualfist, Fishingrod, Rapier, Ancientsword, Crossbow, Flag, Ownthing, Dualdagger)


  case object Sword extends WeaponType {
    override def name: String = "Sword"

    override def fileValue: String = "sword"

    override def code: Int = 0
  }

  case object Blunt extends WeaponType {
    override def name: String = "Blunt"

    override def fileValue: String = "blunt"

    override def code: Int = 1
  }

  case object Dagger extends WeaponType {
    override def name: String = "Dagger"

    override def fileValue: String = "dagger"

    override def code: Int = 2
  }

  case object Bow extends WeaponType {
    override def name: String = "Bow"

    override def fileValue: String = "bow"

    override def code: Int = 3
  }

  case object Pole extends WeaponType {
    override def name: String = "Pole"

    override def fileValue: String = "pole"

    override def code: Int = 4
  }

  case object None extends WeaponType {
    override def name: String = "None"

    override def fileValue: String = "none"

    override def code: Int = 5
  }

  case object Dual extends WeaponType {
    override def name: String = "Dual"

    override def fileValue: String = "dual"

    override def code: Int = 6
  }

  case object Etc extends WeaponType {
    override def name: String = "Etc"

    override def fileValue: String = "etc"

    override def code: Int = 7
  }

  case object Fist extends WeaponType {
    override def name: String = "Fist"

    override def fileValue: String = "fist"

    override def code: Int = 8
  }

  case object Dualfist extends WeaponType {
    override def name: String = "Dualfist"

    override def fileValue: String = "dualfist"

    override def code: Int = 9
  }

  case object Fishingrod extends WeaponType {
    override def name: String = "Fishingrod"

    override def fileValue: String = "fishingrod"

    override def code: Int = 10
  }

  case object Rapier extends WeaponType {
    override def name: String = "Rapier"

    override def fileValue: String = "rapier"

    override def code: Int = 11
  }

  case object Ancientsword extends WeaponType {
    override def name: String = "Ancientsword"

    override def fileValue: String = "ancientsword"

    override def code: Int = 12
  }

  case object Crossbow extends WeaponType {
    override def name: String = "Crossbow"

    override def fileValue: String = "crossbow"

    override def code: Int = 13
  }

  case object Flag extends WeaponType {
    override def name: String = "Flag"

    override def fileValue: String = "flag"

    override def code: Int = 14
  }

  case object Ownthing extends WeaponType {
    override def name: String = "Ownthing"

    override def fileValue: String = "ownthing"

    override def code: Int = 15
  }

  case object Dualdagger extends WeaponType {
    override def name: String = "Dualdagger"

    override def fileValue: String = "dualdagger"

    override def code: Int = 16
  }

}
   
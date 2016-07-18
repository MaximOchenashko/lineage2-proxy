package common.enums.game

import common.enums.base.{FileDataBased, FileDataEnumHolder}

/**
  * @author iRevThis
  */
trait MaterialType extends FileDataBased

object MaterialType extends FileDataEnumHolder[MaterialType] {
  override def values: Seq[MaterialType] = Seq(FineSteel, Steel, Wood, Bone, Bronze, Leather, Cloth, Fish, Gold, Mithril, Liquid, Oriharukon, Damascus, Adamantaite, BloodSteel, Paper, Silver, Chrysolite, Crystal, Horn, ScaleOfDragon, Cotton, Dyestuff, Cobweb, RuneXp, RuneSp, RuneRemovePenalty)


  case object FineSteel extends MaterialType {
    override def name: String = "FineSteel"

    override def fileValue: String = "fine_steel"

    override def code: Int = 0
  }

  case object Steel extends MaterialType {
    override def name: String = "Steel"

    override def fileValue: String = "steel"

    override def code: Int = 1
  }

  case object Wood extends MaterialType {
    override def name: String = "Wood"

    override def fileValue: String = "wood"

    override def code: Int = 2
  }

  case object Bone extends MaterialType {
    override def name: String = "Bone"

    override def fileValue: String = "bone"

    override def code: Int = 3
  }

  case object Bronze extends MaterialType {
    override def name: String = "Bronze"

    override def fileValue: String = "bronze"

    override def code: Int = 4
  }

  case object Leather extends MaterialType {
    override def name: String = "Leather"

    override def fileValue: String = "leather"

    override def code: Int = 5
  }

  case object Cloth extends MaterialType {
    override def name: String = "Cloth"

    override def fileValue: String = "cloth"

    override def code: Int = 6
  }

  case object Fish extends MaterialType {
    override def name: String = "Fish"

    override def fileValue: String = "fish"

    override def code: Int = 7
  }

  case object Gold extends MaterialType {
    override def name: String = "Gold"

    override def fileValue: String = "gold"

    override def code: Int = 8
  }

  case object Mithril extends MaterialType {
    override def name: String = "Mithril"

    override def fileValue: String = "mithril"

    override def code: Int = 9
  }

  case object Liquid extends MaterialType {
    override def name: String = "Liquid"

    override def fileValue: String = "liquid"

    override def code: Int = 10
  }

  case object Oriharukon extends MaterialType {
    override def name: String = "Oriharukon"

    override def fileValue: String = "oriharukon"

    override def code: Int = 11
  }

  case object Damascus extends MaterialType {
    override def name: String = "Damascus"

    override def fileValue: String = "damascus"

    override def code: Int = 12
  }

  case object Adamantaite extends MaterialType {
    override def name: String = "Adamantaite"

    override def fileValue: String = "adamantaite"

    override def code: Int = 13
  }

  case object BloodSteel extends MaterialType {
    override def name: String = "BloodSteel"

    override def fileValue: String = "blood_steel"

    override def code: Int = 14
  }

  case object Paper extends MaterialType {
    override def name: String = "Paper"

    override def fileValue: String = "paper"

    override def code: Int = 15
  }

  case object Silver extends MaterialType {
    override def name: String = "Silver"

    override def fileValue: String = "silver"

    override def code: Int = 16
  }

  case object Chrysolite extends MaterialType {
    override def name: String = "Chrysolite"

    override def fileValue: String = "chrysolite"

    override def code: Int = 17
  }

  case object Crystal extends MaterialType {
    override def name: String = "Crystal"

    override def fileValue: String = "crystal"

    override def code: Int = 18
  }

  case object Horn extends MaterialType {
    override def name: String = "Horn"

    override def fileValue: String = "horn"

    override def code: Int = 19
  }

  case object ScaleOfDragon extends MaterialType {
    override def name: String = "ScaleOfDragon"

    override def fileValue: String = "scale_of_dragon"

    override def code: Int = 20
  }

  case object Cotton extends MaterialType {
    override def name: String = "Cotton"

    override def fileValue: String = "cotton"

    override def code: Int = 21
  }

  case object Dyestuff extends MaterialType {
    override def name: String = "Dyestuff"

    override def fileValue: String = "dyestuff"

    override def code: Int = 22
  }

  case object Cobweb extends MaterialType {
    override def name: String = "Cobweb"

    override def fileValue: String = "cobweb"

    override def code: Int = 23
  }

  case object RuneXp extends MaterialType {
    override def name: String = "RuneXp"

    override def fileValue: String = "rune_xp"

    override def code: Int = 24
  }

  case object RuneSp extends MaterialType {
    override def name: String = "RuneSp"

    override def fileValue: String = "rune_sp"

    override def code: Int = 25
  }

  case object RuneRemovePenalty extends MaterialType {
    override def name: String = "RuneRemovePenalty"

    override def fileValue: String = "rune_remove_penalty"

    override def code: Int = 26
  }

}
   
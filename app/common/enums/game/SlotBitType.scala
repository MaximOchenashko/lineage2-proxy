package common.enums.game

import common.enums.base.{FileDataBased, FileDataEnumHolder}

/**
  * @author iRevThis
  */
trait SlotBitType extends FileDataBased

object SlotBitType extends FileDataEnumHolder[SlotBitType] {
  override def values: Seq[SlotBitType] = Seq(RightHand, LeftRightHand, LeftHand, Chest, Legs, Feet, Head, Gloves, None,
    Onepiece, RightLeftEar, RightLeftFinger, Neck, Back, Underwear, Hair, Alldress, Hair2, Hairall, Rbracelet, Lbracelet, Deco1, Waist)


  case object RightHand extends SlotBitType {
    override def name: String = "Rhand"

    override def fileValue: String = "rhand"

    override def code: Int = 0
  }

  case object LeftRightHand extends SlotBitType {
    override def name: String = "Lrhand"

    override def fileValue: String = "lrhand"

    override def code: Int = 1
  }

  case object LeftHand extends SlotBitType {
    override def name: String = "Lhand"

    override def fileValue: String = "lhand"

    override def code: Int = 2
  }

  case object Chest extends SlotBitType {
    override def name: String = "Chest"

    override def fileValue: String = "chest"

    override def code: Int = 3
  }

  case object Legs extends SlotBitType {
    override def name: String = "Legs"

    override def fileValue: String = "legs"

    override def code: Int = 4
  }

  case object Feet extends SlotBitType {
    override def name: String = "Feet"

    override def fileValue: String = "feet"

    override def code: Int = 5
  }

  case object Head extends SlotBitType {
    override def name: String = "Head"

    override def fileValue: String = "head"

    override def code: Int = 6
  }

  case object Gloves extends SlotBitType {
    override def name: String = "Gloves"

    override def fileValue: String = "gloves"

    override def code: Int = 7
  }

  case object None extends SlotBitType {
    override def name: String = "None"

    override def fileValue: String = "none"

    override def code: Int = 8
  }

  case object Onepiece extends SlotBitType {
    override def name: String = "Onepiece"

    override def fileValue: String = "onepiece"

    override def code: Int = 9
  }

  case object RightLeftEar extends SlotBitType {
    override def name: String = "Rear;lear"

    override def fileValue: String = "rear;lear"

    override def code: Int = 10
  }

  case object RightLeftFinger extends SlotBitType {
    override def name: String = "Rfinger;lfinger"

    override def fileValue: String = "rfinger;lfinger"

    override def code: Int = 11
  }

  case object Neck extends SlotBitType {
    override def name: String = "Neck"

    override def fileValue: String = "neck"

    override def code: Int = 12
  }

  case object Back extends SlotBitType {
    override def name: String = "Back"

    override def fileValue: String = "back"

    override def code: Int = 13
  }

  case object Underwear extends SlotBitType {
    override def name: String = "Underwear"

    override def fileValue: String = "underwear"

    override def code: Int = 14
  }

  case object Hair extends SlotBitType {
    override def name: String = "Hair"

    override def fileValue: String = "hair"

    override def code: Int = 15
  }

  case object Alldress extends SlotBitType {
    override def name: String = "Alldress"

    override def fileValue: String = "alldress"

    override def code: Int = 16
  }

  case object Hair2 extends SlotBitType {
    override def name: String = "Hair2"

    override def fileValue: String = "hair2"

    override def code: Int = 17
  }

  case object Hairall extends SlotBitType {
    override def name: String = "Hairall"

    override def fileValue: String = "hairall"

    override def code: Int = 18
  }

  case object Rbracelet extends SlotBitType {
    override def name: String = "Rbracelet"

    override def fileValue: String = "rbracelet"

    override def code: Int = 19
  }

  case object Lbracelet extends SlotBitType {
    override def name: String = "Lbracelet"

    override def fileValue: String = "lbracelet"

    override def code: Int = 20
  }

  case object Deco1 extends SlotBitType {
    override def name: String = "Deco1"

    override def fileValue: String = "deco1"

    override def code: Int = 21
  }

  case object Waist extends SlotBitType {
    override def name: String = "Waist"

    override def fileValue: String = "waist"

    override def code: Int = 22
  }

}
   
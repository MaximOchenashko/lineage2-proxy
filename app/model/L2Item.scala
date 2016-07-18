package model

import model.embedable.ElementInfo

/**
  * @author iRevThis
  */
case class L2Item(objectId: Int,
                  itemId: Int,
                  equipSlot: Int,
                  count: Long,
                  wType: Int,
                  customType: Int,
                  equipped: Boolean,
                  bodyPart: Int,
                  enchantLevel: Int,
                  customType2: Int,
                  augmentationId: Int,
                  shadowLifeType: Int,
                  temporalLifeTime: Int,
                  elementInfo: ElementInfo,
                  enchantOptions: Array[Int]) extends L2Object {

  val stackable: Boolean = false//todo inject gamedata info?

}

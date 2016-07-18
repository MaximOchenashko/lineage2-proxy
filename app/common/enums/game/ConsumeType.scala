package common.enums.game

import common.enums.base.{FileDataBased, FileDataEnumHolder}

/**
  * @author iRevThis
  */
trait ConsumeType extends FileDataBased

object ConsumeType extends FileDataEnumHolder[ConsumeType] {
  override def values: Seq[ConsumeType] = Seq(Normal, Stackable, Asset)

  case object Normal extends ConsumeType {
    override def name: String = "Normal"

    override def fileValue: String = "consume_type_normal"

    override def code: Int = 0
  }

  case object Stackable extends ConsumeType {
    override def name: String = "Stackable"

    override def fileValue: String = "consume_type_stackable"

    override def code: Int = 1
  }

  case object Asset extends ConsumeType {
    override def name: String = "Asset"

    override def fileValue: String = "consume_type_asset"

    override def code: Int = 2
  }

}

package network.tcp.packets.enums

import common.enums.base.{EnumHolder, EnumLike}

/**
  * @author iRevThis
  */
trait PacketVersion extends EnumLike {
  override def name: String
  override def code: Int
}

object PacketVersion extends EnumHolder[PacketVersion] {
  override def values: Seq[PacketVersion] = Seq(Unknown, HighFive, Epeisodion)

  case object Unknown extends PacketVersion {
    override def code: Int = 0
    override def name: String = "Unknown"
  }

  case object HighFive extends PacketVersion {
    override def code: Int = 1
    override def name: String = "High Five"
  }

  case object Epeisodion extends PacketVersion {
    override def code: Int = 2
    override def name: String = "Epeisodion"
  }
}

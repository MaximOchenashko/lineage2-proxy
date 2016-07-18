package network.tcp.base

import common.enums.base.{EnumHolder, EnumLike}

/**
  * @author iRevThis
  */
trait ConnectionType extends EnumLike

object ConnectionType extends EnumHolder[ConnectionType] {
  override def values: Seq[ConnectionType] = Seq(SpawnListener, WebProxy, ItemBroker)

  case object SpawnListener extends ConnectionType {
    override def code: Int = 0
    override def name: String = "Spawn listener"
  }

  case object WebProxy extends ConnectionType {
    override def code: Int = 1
    override def name: String = "Web proxy"
  }

  case object ItemBroker extends ConnectionType {
    override def code: Int = 2
    override def name: String = "Item broker"
  }
}
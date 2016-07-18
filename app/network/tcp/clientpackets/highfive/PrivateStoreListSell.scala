package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import model.L2Item
import model.embedable.ElementInfo
import network.tcp.clientpackets.highfive.PrivateStoreListSell.{PrivateStoreSellInfo, TradeItem}
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class PrivateStoreListSell(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val sellerId = readD
    val isPackage = readD == 1
    val adena = readQ
    val sellListSize = readD
    val items = Range(0, sellListSize) map { _ =>
      val objectId = readD
      val itemId = readD
      val equipSlot = readD
      val count = readQ
      val wType = readH
      val customType = readH
      val equipped = readH == 1
      val bodyPart = readD
      val enchantLevel = readH
      val customType2 = readH
      val augmentationId = readD
      val shadowLifeTime = readD
      val temporalLifeTime = readD
      val attackElementId = readH
      val attackElementValue = readH
      val defenseFire = readH
      val defenseWater = readH
      val defenceWind = readH
      val defenceEarth = readH
      val defenceHoly = readH
      val defenceUnholy = readH
      val enchantOptions = Range(0, 3).map(_ => readH).toArray

      val ownersPrice = readQ
      val storePrice = readQ

      val elementInfo = ElementInfo(attackElementId, attackElementValue, defenseFire, defenseWater, defenceWind,
                                                                      defenceEarth, defenceHoly, defenceUnholy)

      val item = L2Item(objectId, itemId, equipSlot, count, wType, customType, equipped, bodyPart, enchantLevel,
        customType2, augmentationId, shadowLifeTime, temporalLifeTime, elementInfo, enchantOptions)

      TradeItem(item, ownersPrice, storePrice)
    }

    () => actor ! PrivateStoreSellInfo(sellerId, isPackage, items)
  }

}

object PrivateStoreListSell {
  case class TradeItem(item: L2Item, ownersPrice: Long, storePrice: Long)
  case class PrivateStoreSellInfo(sellerId: Int, isPackage: Boolean, items: Seq[TradeItem])
}

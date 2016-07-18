package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import model.L2Item
import model.embedable.ElementInfo
import network.tcp.clientpackets.highfive.PrivateStoreListBuy.{BuyItem, PrivateStoreBuyInfo}
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class PrivateStoreListBuy(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val customerId = readD
    val playerAdena = readQ
    val buyListSize = readD

    val items = Range(0, buyListSize) map { _ =>
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

      val itemObjectId = readD
      val customerPrice = readQ
      val defaultPrice = readQ
      val storeCount = readQ

      val elementInfo = ElementInfo(attackElementId, attackElementValue, defenseFire, defenseWater, defenceWind,
        defenceEarth, defenceHoly, defenceUnholy)

      val item = L2Item(objectId, itemId, equipSlot, count, wType, customType, equipped, bodyPart, enchantLevel,
        customType2, augmentationId, shadowLifeTime, temporalLifeTime, elementInfo, enchantOptions)

      BuyItem(item, customerPrice, defaultPrice)
    }

    () => actor ! PrivateStoreBuyInfo(customerId, items)
  }

}

object PrivateStoreListBuy {
  case class BuyItem(item: L2Item, ownersPrice: Long, storePrice: Long)
  case class PrivateStoreBuyInfo(sellerId: Int, items: Seq[BuyItem])
}

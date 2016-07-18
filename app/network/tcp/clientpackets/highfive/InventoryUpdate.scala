package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{UpdateType, WebSocketSendableImpl}
import dto.tcp.embeddable.ItemInfo
import model.L2Item
import model.embedable.ElementInfo
import model.prototypes.L2User
import network.tcp.clientpackets.highfive.InventoryUpdate.InventoryUpdateAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class InventoryUpdate(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {
  //вышел из игры
  //он встал
  //15 min
  //
  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val itemsSize: Int = readH
    val items = Range(0, itemsSize).map { i =>
      val updateType: Int = readH
      val objectId: Int = readD
      val itemId: Int = readD
      val equipSlot: Int = readD
      val count: Long = readQ
      val wType: Int = readH
      val customType: Int = readH
      val equipped: Boolean = readH == 1
      val bodyPart: Int = readD
      val enchantLevel: Int = readH
      val customType2: Int = readH
      val augmentationId: Int = readD
      val shadowLifeTime: Int = readD
      val temporalLifeTime: Int = readD
      val attackElementId: Int = readH
      val attackElementValue: Int = readH
      val defenseFire: Int = readH
      val defenseWater: Int = readH
      val defenceWind: Int = readH
      val defenceEarth: Int = readH
      val defenceHoly: Int = readH
      val defenceUnholy: Int = readH

      val enchantOptions = Range(0, 3).map(_ => readH).toArray

      val elementInfo = ElementInfo(attackElementId, attackElementValue, defenseFire, defenseWater, defenceWind,
        defenceEarth, defenceHoly, defenceUnholy)

      val item = L2Item(objectId, itemId, equipSlot, count, wType, customType, equipped, bodyPart, enchantLevel,
        customType2, augmentationId, shadowLifeTime, temporalLifeTime, elementInfo, enchantOptions)

      ItemInfo(updateType, item)
    }.toSeq

    () => actor ! InventoryUpdateAction(items)
  }

}

object InventoryUpdate {

  case class InventoryUpdateAction(items: Seq[ItemInfo]) extends WebSocketSendableImpl with UpdateType[L2User] {
    val objectId = 0

    override def update(obj: L2User): Option[L2User] = {
      Option(obj) foreach (_.updateInventory(items))
      None
    }
  }

}

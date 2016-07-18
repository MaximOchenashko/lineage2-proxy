package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import common.utils.geometry.Location
import network.tcp.clientpackets.highfive.DropItem.DropItemAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class DropItem(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val dropperId = readD
    val itemObjectId = readD
    val itemId = readD
    val x = readD
    val y = readD
    val z = readD
    val stackable = readD == 1
    val count = readQ
    readD

    () => actor ! DropItemAction(dropperId, itemObjectId, itemId, Location(x, y, z), stackable, count)
  }

}

object DropItem {
  case class DropItemAction(dropperId: Int, itemObjId: Int, itemId: Int, loc: Location, stackable: Boolean, count: Long)
}

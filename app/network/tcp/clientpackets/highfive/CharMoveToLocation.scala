package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import common.utils.geometry.Location
import dto.tcp.clientpackets.{UpdateAction, WebSocketSendableImpl}
import network.tcp.clientpackets.highfive.CharMoveToLocation.CharMoveToLocationAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class CharMoveToLocation(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId = readD

    val destX = readD
    val destY = readD
    val destZ = readD

    val currentX = readD
    val currentY = readD
    val currentZ = readD

    () => actor ! CharMoveToLocationAction(objId, new Location(destX, destY, destZ, 0), new Location(currentX, currentY, currentZ, 0))
  }

}

object CharMoveToLocation {
  case class CharMoveToLocationAction(objectId: Int, dest: Location, current: Location) extends WebSocketSendableImpl with UpdateAction
}

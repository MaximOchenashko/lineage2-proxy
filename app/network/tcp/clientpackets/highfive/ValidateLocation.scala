package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import common.utils.geometry.Location
import dto.tcp.clientpackets.{UpdateAction, WebSocketSendableImpl}
import network.tcp.clientpackets.highfive.ValidateLocation.ValidateLocationAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class ValidateLocation(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId = readD
    val x = readD
    val y = readD
    val z = readD
    val h = readD

    () => actor !  ValidateLocationAction(objId, Location(x, y, z, h))
  }

}

object ValidateLocation {

  case class ValidateLocationAction(objectId: Int, loc: Location) extends WebSocketSendableImpl with UpdateAction

}
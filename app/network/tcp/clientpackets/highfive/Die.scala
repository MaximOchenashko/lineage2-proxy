package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import dto.tcp.clientpackets.{UpdateAction, WebSocketSendableImpl}
import network.tcp.clientpackets.highfive.Die.DieAction
import network.tcp.packets.SHexPacket

/**
  * @author iRevThis
  */
case class Die(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId = readD
    /*val toVillage: Int = readD
    val toClanHall: Int = readD
    val toCastle: Int = readD
    val toFlag: Int = readD
    val sweepable: Int = readD
    val fixed: Int = readD
    val toFort: Int = readD
    val showAnimDie: Int = readD
    val agathionRes: Int = readD
    val dummy: Int = readD*/

    () => actor ! DieAction(objId)
  }

}

object Die {

  case class DieAction(objectId: Int) extends WebSocketSendableImpl with UpdateAction

}

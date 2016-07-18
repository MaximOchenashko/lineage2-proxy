package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import enums.ChatType
import network.tcp.clientpackets.highfive.SystemMessage.ChatMessageAction
import network.tcp.packets.SHexPacket

/**
 * @author iRevThis
 */
case class Say2(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val objId = readD
    val chatType = readD
    var senderName = "undefined"

    var npcString: Int = 0 //TODO
    var sysStringId: Int = 0
    var sysMsgId: Int = 0

    val messageArgs = chatType match {
      case 11 =>
        //unhandled
        val sysStringId = readD
        val sysMsgId = readD
        Seq.empty[String]
      case _ =>
        senderName = readS
        npcString = readD
        Stream.from(1).map(i => i -> readS).takeWhile(i => buffer.hasRemaining && i._1 < 5).map(_._2).toSeq
    }

    () => actor ! ChatMessageAction(objId, ChatType.values()(chatType), senderName, messageArgs)
  }

}

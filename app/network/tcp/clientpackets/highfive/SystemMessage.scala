package network.tcp.clientpackets.highfive

import java.nio.ByteBuffer

import akka.actor.ActorRef
import common.utils.DataStorage
import dto.tcp.clientpackets.WebSocketSendableImpl
import enums.ChatType
import network.tcp.clientpackets.highfive.SystemMessage.ChatMessageAction
import network.tcp.packets.SHexPacket
import play.Logger

/**
  * @author iRevThis
  */
case class SystemMessage(hexBuffer: ByteBuffer, actor: ActorRef) extends SHexPacket {

  @throws(classOf[Exception])
  override protected def readImpl: () => Unit = {
    val messageId = readD
    val argsSize = readD
    val messageTemplate = DataStorage.systemMsgs.find(_.id == messageId).map(_.message)

    val replaceValues = Range(0, argsSize) map { i =>
      val argType = readD
      val replaceValue = argType match {
        case 0 =>
          val s: String = readS
          Some(s)
        case 2 =>
          val npcId: Int = readD - 1000000
          DataStorage.npcsData.find(_.id == npcId).map(_.name)
        case 1 | 3 =>
          val itemId: Int = readD
          DataStorage.itemsData.find(_.id == itemId).map(_.name).orElse(Some(itemId.toString))
        case 9 | 10 | 11 | 13 =>
          val var0: Int = readD
          Some(String.valueOf(var0))
        case 8 =>
          val itemId: Int = readD
          val augId: Int = readD
          DataStorage.itemsData.find(_.id == itemId).map(_.name).orElse(Some(itemId.toString))
        case 6 | 12 =>
          val longVal: Long = readQ
          Some(String.valueOf(longVal))
        case 4 =>
          val skillId: Int = readD
          val skillLevel: Int = readD
          DataStorage.skillsData.find(_.id == skillId).map(_.name)
        case 7 =>
          val x: Int = readD
          val y: Int = readD
          val z: Int = readD
          None
        case _ => Logger.info("Unknown sys. message arg type: " + argType); None
      }
      replaceValue -> (i + 1)
    }

    val message = replaceValues.foldLeft(messageTemplate)((template, replace) => replaceString(template, replace._1, replace._2))
    () => actor ! ChatMessageAction(0, ChatType.SYSTEM_MESSAGE, "Sys", Seq(message.getOrElse("System message parse error")))
  }

  private def replaceString(templateOpt: Option[String], replaceWith: Option[String], index: Int): Option[String] = replaceWith match {
    case Some(replace) =>
      templateOpt map {
        case that if that.contains("$c" + index) => that.replace("$c" + index, replace)
        case that if that.contains("$s" + index) => that.replace("$s" + index, replace)
        case any => any
      }
    case None => templateOpt
  }
}

object SystemMessage {
  case class ChatMessageAction(objId: Int, cType: ChatType, senderName: String, args: Seq[String]) extends WebSocketSendableImpl
}

package network.tcp.packets.handlers

import java.nio.ByteBuffer

import akka.actor.ActorRef
import network.errors.{NetworkError, UnknownPacket}
import network.tcp.base.ConnectionType
import network.tcp.base.ConnectionType.{ItemBroker, SpawnListener, WebProxy}
import network.tcp.clientpackets.highfive._
import network.tcp.packets.base.ReceivablePacket
import network.tcp.packets.handlers.PacketHandler.{PacketReadResult, packetReader}

import scalaz.Scalaz._
import scalaz._

/**
  * @author iRevThis
  */
final class ClientPacketHandler(connectionType: ConnectionType /*, packetFactory: PacketFactory*/) extends PacketHandler {

  override def handlePacket(buf: ByteBuffer): \/[NetworkError, PacketReadResult] = {
    val opCode = buf.get & 0xFF
    val matcher = connectionType match {
      case SpawnListener => spawnListenerMatcher
      case WebProxy => webProxyMatcher
      case ItemBroker => itemBrokerMatcher
    }

    matcher.andThen(v => packetReader(v).right).applyOrElse(opCode, (opCode: Int) => UnknownPacket(opCode).left)
  }

  private val spawnListenerMatcher: PartialFunction[Int, (ByteBuffer, ActorRef) => ReceivablePacket] = {
    case 0x00 => Die.apply
    case 0x0C => NpcInfo.apply
    case 0x16 => DropItem.apply
  }

  private val webProxyMatcher: PartialFunction[Int, (ByteBuffer, ActorRef) => ReceivablePacket] = {
    case 0x00 => Die.apply
    case 0x01 => Revive.apply
    case 0x08 => DeleteObject.apply
    case 0x0C => NpcInfo.apply
    case 0x16 => DropItem.apply
    case 0x18 => StatusUpdate.apply
    case 0x21 => InventoryUpdate.apply
    case 0x23 => TargetSelected.apply
    case 0x24 => TargetUnselected.apply
    case 0x2f => CharMoveToLocation.apply
    case 0x31 => CharInfo.apply
    case 0x32 => UserInfo.apply
    case 0x4A => Say2.apply
    case 0x4E => PartySmallWindowAll.apply
    case 0x4F => PartySmallWindowAdd.apply
    case 0x50 => PartySmallWindowDeleteAll.apply
    case 0x51 => PartySmallWindowDelete.apply
    case 0x52 => PartySmallWindowUpdate.apply
    case 0x62 => SystemMessage.apply
    case 0x79 => ValidateLocation.apply
    case 0x85 => AbnormalStatusUpdate.apply
    case 0xF4 => PartySpelled.apply
    case 0xF9 => EtcStatusUpdate.apply
    case 0xFA => ShortBuffStatusUpdate.apply
    case 0xA1 => PrivateStoreListSell.apply
    case 0xBE => PrivateStoreListBuy.apply
  }

  private val itemBrokerMatcher: PartialFunction[Int, (ByteBuffer, ActorRef) => ReceivablePacket] = {
    case 0x31 => CharInfo.apply
    case 0x32 => UserInfo.apply
    case 0xA1 => PrivateStoreListSell.apply
    case 0xBE => PrivateStoreListBuy.apply
    case 0x08 => DeleteObject.apply
  }

}

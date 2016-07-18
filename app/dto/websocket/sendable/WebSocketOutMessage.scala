package dto.websocket.sendable

import actors.websocket.WSConnectionHandler.TcpSocketDie
import dto.tcp.clientpackets._
import dto.tcp.custom.UserStatsDTO
import dto.websocket.local.AssociatedTcpSocket
import enums.WebSocketSendableMessageType
import model.prototypes.{L2Char, L2Npc, L2User}
import network.tcp.clientpackets.highfive.AbnormalStatusUpdate.AbnormalStatusUpdateAction
import network.tcp.clientpackets.highfive.CharMoveToLocation.CharMoveToLocationAction
import network.tcp.clientpackets.highfive.DeleteObject.DeleteObjectAction
import network.tcp.clientpackets.highfive.Die.DieAction
import network.tcp.clientpackets.highfive.EtcStatusUpdate.EtcStatusUpdateAction
import network.tcp.clientpackets.highfive.InventoryUpdate.InventoryUpdateAction
import network.tcp.clientpackets.highfive.PartySmallWindowAdd.PartySmallWindowAddAction
import network.tcp.clientpackets.highfive.PartySmallWindowAll.PartySmallWindowAllAction
import network.tcp.clientpackets.highfive.PartySmallWindowDelete.PartySmallWindowDeleteAction
import network.tcp.clientpackets.highfive.PartySmallWindowUpdate.PartySmallWindowUpdateAction
import network.tcp.clientpackets.highfive.PartySpelled.PartySpelledDTO
import network.tcp.clientpackets.highfive.Revive.ReviveAction
import network.tcp.clientpackets.highfive.StatusUpdate.StatusUpdateAction
import network.tcp.clientpackets.highfive.SystemMessage.ChatMessageAction
import network.tcp.clientpackets.highfive.TargetSelected.TargetSelectedAction
import network.tcp.clientpackets.highfive.TargetUnselected.TargetUnselectedAction
import network.tcp.clientpackets.highfive.ValidateLocation.ValidateLocationAction
import play.api.libs.json.{JsString, JsValue, Json, Writes}

/**
 * @author iRevThis
 */
final case class WebSocketOutMessage[X <: WebSocketSendable](val underlying: X)(implicit writer: Writes[X]) {
  val messageType = WebSocketOutMessage.messageMatcher(underlying)
  lazy val container: JsValue = Json.toJson(underlying)
}

object WebSocketOutMessage {

  implicit val webSocketOutMessageWrites: Writes[WebSocketOutMessage[_]] = new Writes[WebSocketOutMessage[_]] {
    override def writes(o: WebSocketOutMessage[_]): JsValue =
      Json.obj(
        "messageType" -> JsString(o.messageType.name()),
        "container" -> o.container
      )
  }
  
  private val messageMatcher: PartialFunction[WebSocketSendable, WebSocketSendableMessageType] = {
    case _: DieAction => WebSocketSendableMessageType.DIE
    case _: ReviveAction => WebSocketSendableMessageType.REVIVE
    case _: StatusUpdateAction => WebSocketSendableMessageType.STATUS_UPDATE
    case _: TargetSelectedAction => WebSocketSendableMessageType.TARGET_SELECTED
    case _: TargetUnselectedAction => WebSocketSendableMessageType.TARGET_UNSELECTED
    case _: ChatMessageAction => WebSocketSendableMessageType.CHAT_MESSAGE
    case _: PartySmallWindowAddAction => WebSocketSendableMessageType.PARTY_SMALL_WINDOW_ADD
    case _: PartySmallWindowAllAction => WebSocketSendableMessageType.PARTY_SMALL_WINDOW_ALL
    case _: PartySmallWindowDeleteAction => WebSocketSendableMessageType.PARTY_SMALL_WINDOW_DELETE
    //case _: PartySmallWindowDeleteAllDTO => WebSocketSendableMessageType.PARTY_SMALL_WINDOW_DELETE_ALL
    case _: PartySmallWindowUpdateAction => WebSocketSendableMessageType.PARTY_SMALL_WINDOW_UPDATE
    case _: AbnormalStatusUpdateAction => WebSocketSendableMessageType.ABNORMAL_STATUS_UPDATE
    case _: PartySpelledDTO => WebSocketSendableMessageType.PARTY_SPELLED
    case _: EtcStatusUpdateAction => WebSocketSendableMessageType.ETC_STATUS_UPDATE
    case _: L2Npc => WebSocketSendableMessageType.NPC_INFO
    case _: L2Char => WebSocketSendableMessageType.CHAR_INFO
    case _: L2User => WebSocketSendableMessageType.USER_INFO
    //ObjectInfoListContainer.class, WebSocketSendableMessageType.SHORT_INFO_LIST;
    case _: InventoryUpdateAction => WebSocketSendableMessageType.INVENTORY_UPDATE
    case _: ValidateLocationAction => WebSocketSendableMessageType.VALIDATE_LOCATION
    case _: CharMoveToLocationAction => WebSocketSendableMessageType.CHAR_MOVE_TO_LOCATION
    case _: AssociatedTcpSocket => WebSocketSendableMessageType.ASSOCIATED_SOCKET
    case _: TcpSocketDie => WebSocketSendableMessageType.TCP_SOCKET_DIE
    case _: DeleteObjectAction => WebSocketSendableMessageType.DELETE_OBJECT
    case _: UserStatsDTO => WebSocketSendableMessageType.USER_STATS
    case _ => WebSocketSendableMessageType.NOT_DONE
  }

}

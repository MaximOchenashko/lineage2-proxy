package dto.websocket.local

import dto.tcp.clientpackets.WebSocketSendableImpl
import play.api.libs.json.{Json, Writes}

/**
  * @author iRevThis
  */
final case class AssociatedTcpSocket(userName: String) extends WebSocketSendableImpl

object AssociatedTcpSocket {
  implicit val writes: Writes[AssociatedTcpSocket] = Json.writes[AssociatedTcpSocket]
}

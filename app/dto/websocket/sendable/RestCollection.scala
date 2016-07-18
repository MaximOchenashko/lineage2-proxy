package dto.websocket.sendable

import dto.tcp.clientpackets.WebSocketSendable
import play.api.libs.json.{Json, JsValue, Writes}

/**
 * @author iRevThis
 */
case class RestCollection[X](items: Seq[X]) extends WebSocketSendable {
  val count = if (items != null && items.nonEmpty) items.size else 0
}

object RestCollection {
  implicit def writes[X](implicit w: Writes[X]): Writes[RestCollection[X]] = new Writes[RestCollection[X]] {
    override def writes(o: RestCollection[X]): JsValue =
      Json.obj(
        "items" -> Json.toJson(o.items),
        "count" -> o.count
      )
  }
}

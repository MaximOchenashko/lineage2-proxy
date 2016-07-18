package dto.websocket

import enums.ChatType
import play.api.libs.json.{JsSuccess, JsResult, JsValue, Json, Reads}

/**
  * @author iRevThis
  */
package object receivable {

  final case class WSMessageWrapper(messageType: String, payload: JsValue)

  final case class Token(token: String, userName: String) extends ReceivableMessage

  final case class ChatMessage(text: String, chatType: ChatType, target: Option[String], userName: String) extends ReceivableMessageImpl

  object WSMessageWrapper {

    implicit val reads: Reads[WSMessageWrapper] = Json.reads[WSMessageWrapper]

  }

  object Token {
    implicit val reads: Reads[Token] = Json.reads[Token]
  }

  object ChatMessage {
    implicit val chatTypeReads: Reads[ChatType] = new Reads[ChatType] {
      override def reads(json: JsValue): JsResult[ChatType] = JsSuccess(ChatType.valueOf(json.as[String]))
    }
    implicit val reads: Reads[ChatMessage] = Json.reads[ChatMessage]
  }
}

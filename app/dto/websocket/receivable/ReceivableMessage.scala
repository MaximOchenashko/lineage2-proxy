package dto.websocket.receivable

/**
  * @author iRevThis
  */
trait ReceivableMessage

trait ReceivableMessageImpl extends ReceivableMessage {
  def userName: String
}
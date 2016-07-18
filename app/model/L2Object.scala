package model

import dto.tcp.clientpackets.WebSocketSendable

/**
 * @author iRevThis
 */
trait L2Object extends WebSocketSendable {
  def objectId: Int
}

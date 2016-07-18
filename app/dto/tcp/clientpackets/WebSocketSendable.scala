package dto.tcp.clientpackets

import java.time.{OffsetDateTime, ZoneId}

/**
 * @author iRevThis
 */
trait WebSocketSendable {
  val createTime: Long = OffsetDateTime.now().toEpochSecond
  val zoneId: ZoneId = ZoneId.systemDefault
}

abstract class WebSocketSendableImpl extends WebSocketSendable

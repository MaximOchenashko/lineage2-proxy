package network.errors

import scalaz._
import Scalaz._

/**
  * @author iRevThis
  */
abstract class NetworkError(val message: String, cause: Option[Throwable] = None)

final case class UnknownPacket(opCode: Int) extends NetworkError(s"Unknown packet. Op code: $opCode")

final case class ThrowableError(cause: Throwable) extends NetworkError("Throwable error", cause.some)

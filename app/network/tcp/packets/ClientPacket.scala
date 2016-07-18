package network.tcp.packets

import javax.xml.bind.DatatypeConverter

import network.tcp.packets.base.ReceivablePacket
import play.api.Logger

import scala.util.control.NonFatal
import scalaz.{-\/, \/, \/-}

/**
  * @author iRevThis
  */
abstract class ClientPacket extends ReceivablePacket {

  override def read: Throwable \/ (() => Unit) =
    \/ fromTryCatchNonFatal readImpl

  def readAndExecute(): Unit =
    read match {
      case -\/(e) =>
        Logger.error(s"Failed reading: [C] ${getClass.getSimpleName}", e)
      case \/-(func) =>
        try func()
        catch {
          case NonFatal(e) => Logger.error(s"Failed executing: [C] ${getClass.getSimpleName}", e)
        }
    }

  @throws(classOf[Exception])
  protected def readImpl: () => Unit

  protected def hexStringToByteArray(s: String): Array[Byte] = {
    DatatypeConverter.parseHexBinary(s)
    //s.grouped(2).map(cc => ((Character.digit(cc(0), 16) << 4) + Character.digit(cc(1), 16)).toByte).toArray
  }

}

package actors.tcp

import java.nio.ByteBuffer

import actors.tcp.AuthActor.AuthInfo
import actors.tcp.TcpConnectionHandler._
import actors.tcp.connection.types.{ItemBroker, SpawnListener, WebProxy}
import akka.actor._
import akka.util.ByteString
import network.config.ConnectionConfig
import network.tcp.base.ConnectionType.{ItemBroker, SpawnListener, WebProxy}
import network.tcp.packets.handlers.{ClientPacketHandler, PacketHandler}
import play.api.{Logger, Play}

/**
  * @author iRevThis
  */
final class TcpConnectionHandler(val remoteConnect: ActorRef,
                                 tcpManager: ActorRef,
                                 val authInfo: AuthInfo,
                                 cfg: ConnectionConfig) extends Actor
  with ActorLogging
  with SpawnListener
  with WebProxy
  with ItemBroker {

  private val BufferSize = cfg.bufferSize
  private val PacketHeaderSize = cfg.packetHeaderSize

  context watch remoteConnect

  val packetHandler = new ClientPacketHandler(authInfo.connectionType)
  var user = authInfo.user
  val buffer = ByteBuffer.allocate(BufferSize)

  val server: String = authInfo.server
  val token: String = authInfo.token
  val userName: String = authInfo.user.name
  var totalPackets = 0

  lazy val databaseManager = context.system.actorSelection("akka://application/user/databaseManager")

  authInfo.connectionType match {
    case SpawnListener =>
      context.become(spawnListenerReceive)
    case WebProxy =>
      context.become(webProxyReceive)
    case ItemBroker =>
      context.become(itemBrokerReceive)
    case _ =>
      log.error("Unknown connection type")
  }

  override def receive: Receive = {
    case any => unhandled(any)
  }

  protected def handleNewData(data: ByteString) = {
    //Logger.info(s"Remaining: ${buffer.remaining()}. New data: ${data.toArray.length}")
    buffer.put(data.toArray)
    totalPackets = totalPackets + Stream.continually(tryReadPacket).takeWhile(_ == true).length
  }

  /**
    * Try parse packet from current buffer.
    * If buffer remaining bytes < data header(2 bytes) - false
    * If buffer remaining bytes < packet size - false
    * Otherwise - true
    */
  private[this] def tryReadPacket: Boolean = {
    //checks that we have more bytes in buffer than PacketHeaderSize
    buffer.position > PacketHeaderSize match {
      case true =>
        //remember current position for rollback
        val pos = buffer.position
        buffer.position(0)
        //reads packet size and subtract HEADER_SIZE
        val packetSize = (buffer.getShort & 0xFFFF) - PacketHeaderSize
        if (packetSize < 0)
          throw new RuntimeException(s"Invalid packet size: $packetSize")
        //checks that packetSize is positive and verify that buffer has enough bytes for packet
        pos - PacketHeaderSize > packetSize match {
          case true =>
            val packet: ByteBuffer = ByteBuffer.allocate(packetSize)
            val packetData: Array[Byte] = new Array[Byte](packetSize)
            buffer.get(packetData, 0, packetSize)
            packet.put(packetData)
            val bufferData: Array[Byte] = new Array[Byte](buffer.remaining)
            buffer.get(bufferData)
            buffer.clear
            buffer.put(bufferData)
            buffer.position(pos - (packetSize + PacketHeaderSize))
            parsePacket(packetHandler, packet)
            true

          case false =>
            buffer.position(pos)
            false //rollback buffer reading
        }
      case false => false
    }
  }

  /**
    * Parse packet and execute it
    *
    * @param packetHandler //
    * @param buffer        readable buffer with packet
    */
  private[this] def parsePacket(packetHandler: PacketHandler, buffer: ByteBuffer): Unit = {
    buffer.flip
    if (buffer.hasRemaining) {
      packetHandler.handlePacket(buffer) foreach { f =>
        f(buffer, self).readAndExecute()
      }
    }
  }

  override def aroundPostStop(): Unit = {
    log.info(s"Total packets: $totalPackets")
    super.aroundPostStop()
  }
}


object TcpConnectionHandler {

  def props(remoteConnect: ActorRef, tcpManager: ActorRef, authInfo: AuthInfo, cfg: ConnectionConfig) =
    Props(classOf[TcpConnectionHandler], remoteConnect, tcpManager, authInfo, cfg)

}
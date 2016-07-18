package network.tcp.packets.base

import java.nio.ByteBuffer

import akka.actor.ActorRef

/**
  * @author iRevThis
  */
trait AbstractPacket {

  def buffer: ByteBuffer
  def actor: ActorRef

}

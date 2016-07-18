package actors.tcp

import java.net.InetSocketAddress

import actors.tcp.AuthActor.AuthInfo
import actors.tcp.TcpConnectionsManager._
import actors.tcp.TcpServerActor._
import akka.actor._
import akka.io.{IO, Tcp, TcpMessage}
import akka.pattern.ask
import akka.util.Timeout
import network.config.ConnectionConfig
import play.api.Configuration

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author iRevThis
  */
final class TcpServerActor(manager: ActorRef, configuration: Configuration) extends Actor {

  val bindAddress = configuration.getString("ServerAddress") getOrElse "127.0.0.1"
  val bindPort = configuration.getInt("ServerPort") getOrElse 7778

  val connectionConfig = {
    val bufferSize = configuration.getInt("ReaderBufferSize") getOrElse 32768
    val packetHeaderSize = configuration.getInt("PacketHeaderSize") getOrElse 2
    ConnectionConfig(bufferSize, packetHeaderSize)
  }

  override def receive: Receive = {
    case t: Tcp.Bound =>
      manager ! t

    case t: Tcp.Connected =>
      manager ! t
      val handler: ActorRef = context.actorOf(AuthActor.props(sender, self, connectionConfig))
      sender ! TcpMessage.register(handler, keepOpenOnPeerClosed = true, useResumeWriting = true) //todo check implicits

    case t@BindNewConnection(remoteConnect, authActor, authInfo) =>
      val token = authInfo.token
      context.child(token) match {
        case Some(actor) => actor forward t
        case None => context.actorOf(TcpConnectionsManager.props(token, connectionConfig), token) forward t
      }

    case _: Tcp.CommandFailed =>
      context stop self
  }

  override def preStart(): Unit =
    manager ! TcpMessage.bind(self, new InetSocketAddress(bindAddress, bindPort), 100)

  override def postStop(): Unit = {
    manager ! TcpMessage.close
    manager ! TcpMessage.unbind
  }
}

object TcpServerActor {
  val actorName = "server"

  def props(configuration: Configuration)(implicit system: ActorSystem) = Props(classOf[TcpServerActor], IO(Tcp), configuration)

  case class BindNewConnection(remoteConnect: ActorRef, authActor: ActorRef, authInfo: AuthInfo)

  case class ClientMessage(userNames: Seq[String], token: String, sender: ActorRef, message: Any)

  case class ActorExists(userName: String, token: String)

  def <<!(message: ClientMessage)(implicit system: ActorSystem) =
    system.actorSelection(system / actorName / message.token.toUpperCase) tell(MessageTo(message.userNames, message.message), message.sender)

  def actorsExists(token: String)(implicit system: ActorSystem, ec: ExecutionContext, timeout: Timeout): Future[Boolean] =
    system.actorSelection(system / actorName / token.toUpperCase)
      .resolveOne()
      .map { case _ => true }
      .recover { case t: Throwable => false }

  def tcpConnectionNamesByToken(token: String)(implicit system: ActorSystem, ec: ExecutionContext, timeout: Timeout): Future[Seq[String]] =
    system.actorSelection(system / actorName / token.toUpperCase) ? RequestNames map {
      case NamesList(names) => names
      case _ => Seq.empty[String]
    } recover { case _ => Seq.empty[String] }

  def actorsExists(message: ActorExists)(implicit system: ActorSystem, ec: ExecutionContext, timeout: Timeout): Future[Boolean] =
    system.actorSelection(system / actorName / message.token.toUpperCase) ? NameExists(message.userName) map {
      case NameExist(value) => value
      case _ => false
    } recover { case t: Throwable => false }

}

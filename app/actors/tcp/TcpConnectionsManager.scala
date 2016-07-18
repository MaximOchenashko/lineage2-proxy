package actors.tcp

import java.nio.BufferOverflowException

import actors.tcp.AuthActor.RegisterTcpHandler
import actors.tcp.TcpConnectionsManager._
import actors.tcp.TcpServerActor.BindNewConnection
import akka.actor.SupervisorStrategy.Stop
import akka.actor.{ActorLogging, Actor, OneForOneStrategy, Props, SupervisorStrategy}
import network.config.ConnectionConfig

import scala.concurrent.duration._

/**
  * @author iRevThis
  */
final class TcpConnectionsManager(token: String, cfg: ConnectionConfig) extends Actor with ActorLogging {

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy(10, 1.minute, loggingEnabled = true) {
    case e: BufferOverflowException =>
      log.error(e, "Buffer overflow")
      Stop
    case e: Throwable =>
      log.error(e, "Another error")
      Stop
  }

  override def receive: Receive = {
    case BindNewConnection(rConnect, authActor, authInfo) =>
      context.child(authInfo.user.name) match {
        case Some(x) => context.stop(x)
        case _ =>
      }
      //todo check if exists actor and etc
      val handler = context.actorOf(TcpConnectionHandler.props(rConnect, self, authInfo, cfg), authInfo.user.name)
      authActor ! RegisterTcpHandler(handler)

    case RequestNames =>
      sender ! NamesList(context.children.map(_.path.name).toSeq)

    case NameExists(name) =>
      sender ! NameExist(context.children.map(_.path.name).exists(_ == name))

    case MessageTo(names, message) =>
      context.children.filter(a => names.contains(a.path.name)).foreach(_ forward message)

    case _ =>
  }

}

object TcpConnectionsManager {
  //requests
  case object RequestNames
  case class NameExists(name: String)

  //response
  case class NamesList(names: Seq[String])
  case class NameExist(exists: Boolean)

  //etc
  case class MessageTo(names: Seq[String], message: Any)

  def props(token: String, cfg: ConnectionConfig) = Props(classOf[TcpConnectionsManager], token, cfg)
}

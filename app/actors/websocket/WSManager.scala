package actors.websocket

import java.util.concurrent.TimeUnit

import actors.websocket.WSByNameManager.ProxyMessage
import actors.websocket.WSManager.Register
import akka.actor.{ActorSystem, Actor, ActorRef, Props}
import akka.util.Timeout

/**
  * @author iRevThis
  */
final class WSManager extends Actor {

  override def receive: Receive = {
    case r @ Register(token, username, out, authActor) =>
      context.child(token) match {
        case Some(actor) => actor forward r
        case None => context.actorOf(WSConnectionsManager.props(token), token) forward r
      }
  }

}

object WSManager {
  val actorName = "web-socket-manager"
  implicit val timeout = Timeout(10, TimeUnit.SECONDS)
  def props = Props(classOf[WSManager])

  case class Register(token: String, username: String, out: ActorRef, authActor: ActorRef)

  case class WSMessage(token: String, userName: String, sender: ActorRef, proxyMessage: ProxyMessage)

  def register(register: Register)(implicit system: ActorSystem) =
    system.actorSelection(system / actorName) ! register

  def <<!(message: WSMessage)(implicit system: ActorSystem) =
    system.actorSelection(system / actorName / message.token / message.userName) tell (message.proxyMessage, message.sender)

}

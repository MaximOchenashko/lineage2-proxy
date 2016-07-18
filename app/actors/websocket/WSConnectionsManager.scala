package actors.websocket

import actors.websocket.WSConnectionsManager.{MessageTo, MessageToAll}
import actors.websocket.WSManager.Register
import akka.actor.{Actor, Props}

/**
  * Init context for user token
  *
  * @param token api token
  * @author iRevThis
  */
final class WSConnectionsManager(token: String) extends Actor {

  override def receive: Receive = {
    case r@Register(t, u, out, _) =>
      context.child(u) match {
        case Some(actor) => actor forward r
        case None => context.actorOf(WSByNameManager.props, u) forward r
      }

    case MessageTo(username, message) =>
      context.child(username) foreach (_ forward message)

    case MessageToAll(message) =>
      context.children foreach (_ forward message)
  }

}

object WSConnectionsManager {

  case class MessageTo(username: String, message: Any)

  case class MessageToAll(message: Any)

  def props(token: String) = Props(classOf[WSConnectionsManager], token)
}

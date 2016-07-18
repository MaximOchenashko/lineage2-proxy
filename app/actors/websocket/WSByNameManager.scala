package actors.websocket

import actors.websocket.WSAuth.EndpointHandler
import actors.websocket.WSByNameManager.ProxyMessage
import actors.websocket.WSManager.Register
import akka.actor.{Actor, Props}

/**
  * @author iRevThis
  */
/**
  * Handle all connection for user token by name, i.e. user can open two similar connection from different browsers. For e.g.:
  * Token -> Username1 => [Username1#a, Username1#b, Username1#c]
  */
final class WSByNameManager extends Actor {

  override def receive: Receive = {
    case Register(token, username, out, authActor) =>
      val handler = context.actorOf(WSConnectionHandler.props(out, token, username))
      authActor ! EndpointHandler(handler)

    case ProxyMessage(message) =>
      context.children.foreach(_ forward message)
  }

}

object WSByNameManager {
  case class ProxyMessage(message: Any)
  def props = Props(classOf[WSByNameManager])
}

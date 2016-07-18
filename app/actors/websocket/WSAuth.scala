package actors.websocket

import actors.websocket.WSAuth.{EndpointHandler, Register}
import akka.actor.{Actor, ActorRef, Props}

/**
  * @author iRevThis
  */
final class WSAuth(token: String, username: String, out: ActorRef) extends Actor {

  import context.system

  override def receive: Receive = {
    case Register =>
      WSManager register WSManager.Register(token, username, out, self)

    case EndpointHandler(handler) =>
      context.become(connected(handler))
  }

  def connected(handler: ActorRef): Receive = {
    case t: Any => handler forward t
  }
}

object WSAuth {
  case object Register
  case class EndpointHandler(actor: ActorRef)
  def props(token: String, username: String, out: ActorRef) = Props(classOf[WSAuth], token, username, out)
}

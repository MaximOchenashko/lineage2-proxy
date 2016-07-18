package actors.websocket

import java.util.concurrent.TimeUnit

import actors.tcp.TcpServerActor
import actors.tcp.TcpServerActor.{ActorExists, ClientMessage}
import akka.actor._
import akka.util.Timeout
import dto.tcp.clientpackets.WebSocketSendable
import dto.websocket.local.AssociatedTcpSocket
import dto.websocket.receivable.{ChatMessage, ReceivableMessage, Token, WSMessageWrapper}
import dto.websocket.sendable.WebSocketOutMessage
import play.api.libs.json.{JsString, Writes, Format, JsValue, Json}

import scala.collection.mutable
import scalaz.{-\/, \/, \/-}

/**
 * @author iRevThis
 */
final class WSConnectionHandler(out: ActorRef, token: String, username: String) extends Actor with ActorLogging  {

  import WSConnectionHandler._
  import context.system
  import context.dispatcher

  context watch out

  implicit val timeout = Timeout(10, TimeUnit.SECONDS)

  val activeTcpSockets = mutable.HashMap.empty[String, ActorRef]

  override def receive: Receive = {
    case that: JsValue =>
      handleStringMessage(that)

    case that: WebSocketOutMessage[_] =>
      sendMessage(that)

    case that: AssociatedTcpSocket =>
      activeTcpSockets += ((that.userName, sender()))
      sendMessage(WebSocketOutMessage(that))

    case t @ TcpSocketDie(uname, act) =>
      activeTcpSockets -= uname
      sendMessage(WebSocketOutMessage(t))

    case Terminated(actor) if actor == out =>
      log.info("Out actor is terminated")
      context.system stop self
  }

  @throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    if (activeTcpSockets.contains(username))
      activeTcpSockets.get(username).get ! WebSocketDie(username)

    super.postStop()
  }

  def sendMessage(message: WebSocketOutMessage[_ <: WebSocketSendable]): Unit = {
    out ! Json.toJson(message)
  }

  def handleStringMessage(message: JsValue): Unit =
    deserialzie(message) match {
      case -\/(e) =>
        log.error("WSMessage deserializing error. Json: {}", message)

      case \/-(Token(tok, uName)) =>
        TcpServerActor.actorsExists(ActorExists(uName, tok)) map {
          case false =>
            sendMessage(new WebSocketOutMessage(new TcpSocketDie("NoUsers", ActorRef.noSender)))
            context stop self
          case true =>
            TcpServerActor <<! ClientMessage(Seq(uName), tok, self, NewWebSocket(tok, uName))
        }

      case \/-(chatMsg: ChatMessage) =>
        val tcpSocket = activeTcpSockets(chatMsg.userName)
        if (tcpSocket != null)
          tcpSocket ! chatMsg

      case _ =>
    }


}

object WSConnectionHandler {

  sealed trait LocalEvents
  case class WebSocketDie(username: String) extends LocalEvents
  case class TcpSocketDie(username: String, actorRef: ActorRef) extends WebSocketSendable
  case class NewWebSocket(token: String, userName: String)

  def props(out: ActorRef, token: String, username: String) = Props(classOf[WSConnectionHandler], out, token, username)

  private def deserialzie(source: JsValue): Throwable \/ ReceivableMessage =
    \/ fromTryCatchNonFatal {
      val wrapper = source.as[WSMessageWrapper]
      messageTypeMatcher(wrapper.messageType)(wrapper.payload)
    }

  private val messageTypeMatcher: PartialFunction[String, JsValue => ReceivableMessage] = {
    case "TOKEN" => _.as[Token]
    case "CHAT_MESSAGE" => _.as[ChatMessage]
  }

  object TcpSocketDie {
    implicit val format: Writes[TcpSocketDie] = Writes(r => JsString(r.username))
  }

}

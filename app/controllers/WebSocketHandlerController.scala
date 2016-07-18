package controllers

import javax.inject.{Inject, Singleton}

import actors.websocket.WSAuth
import akka.actor.ActorSystem
import akka.stream.Materializer
import controllers.base.BaseController
import play.api.libs.json.JsValue
import play.api.libs.streams.ActorFlow
import play.api.mvc.WebSocket
import services.security.AuthService

import scala.concurrent.Future

/**
  * @author iRevThis
  */
@Singleton
class WebSocketHandlerController @Inject()(val authService: AuthService)
                                          (implicit system: ActorSystem, mat: Materializer) extends BaseController {

  def handleWebSocket = WebSocket.acceptOrResult[JsValue, JsValue] { request =>
    Future.successful {
      val wsHandler = for {
        token <- request.getQueryString("token")
        username <- request.getQueryString("username")
      } yield ActorFlow.actorRef(outActor => WSAuth.props(token, username, outActor))
      wsHandler.toRight(Forbidden)
    }
  }

}

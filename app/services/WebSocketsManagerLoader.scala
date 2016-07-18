package services

import javax.inject.{Inject, Singleton}

import actors.websocket.WSManager
import akka.actor.ActorSystem
import play.api.inject.ApplicationLifecycle

/**
 * @author iRevThis
 */
@Singleton
class WebSocketsManagerLoader @Inject()(system: ActorSystem, lifecycle: ApplicationLifecycle) {

  system.actorOf(WSManager.props, WSManager.actorName)

}

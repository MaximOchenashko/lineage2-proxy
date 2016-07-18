package services

import javax.inject.{Inject, Singleton}

import actors.db.DatabaseManager
import actors.tcp.TcpServerActor
import actors.utils.DeadLetterActor
import akka.actor.{ActorRef, ActorSystem, DeadLetter, Props}
import common.utils.DataStorage
import play.api.Configuration
import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.ApplicationLifecycle

/**
 * @author iRevThis
 */
@Singleton
class ServerLoader @Inject()(implicit val system: ActorSystem,
                             dbConfigProvider: DatabaseConfigProvider,
                             configuration: Configuration,
                             lifecycle: ApplicationLifecycle) {

  val deadLetterActor: ActorRef = system.actorOf(Props(classOf[DeadLetterActor]))
  system.eventStream.subscribe(deadLetterActor, classOf[DeadLetter])
  system.actorOf(TcpServerActor.props(configuration), TcpServerActor.actorName)
  system.actorOf(DatabaseManager.props(dbConfigProvider, configuration), DatabaseManager.actorName)
  DataStorage.skillsData //just init

  //configuration.getBoolean("createTemplates").filter(_ == true).foreach(_ => itemDataService.forceLoad())
  //configuration.getBoolean("createUnknown").filter(_ == true).foreach(_ => itemDataService.fillUnknown())

}

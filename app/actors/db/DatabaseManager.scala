package actors.db

import actors.db.DatabaseWorker.DatabaseWorkerMessage
import akka.actor.{Actor, ActorLogging, Props}
import akka.routing.BalancingPool
import play.api.Configuration
import play.api.db.slick.DatabaseConfigProvider

/**
  * @author iRevThis
  */
class DatabaseManager(dbConfigProvider: DatabaseConfigProvider,
                      configuration: Configuration) extends Actor with ActorLogging {

  val poolSize = configuration.getInt("slick.dbs.default.db.numThreads") getOrElse 1
  val router = context.actorOf(BalancingPool(poolSize).props(DatabaseWorker.props(dbConfigProvider)), "dbRouter")

  log.info(s"Database manager started with path: ${self.path}")

  override def receive: Receive = {
    case x: DatabaseWorkerMessage => router forward x
  }

}

object DatabaseManager {
  val actorName = "databaseManager"
  def props(dbConfigProvider: DatabaseConfigProvider, configuration: Configuration) =
    Props(classOf[DatabaseManager], dbConfigProvider, configuration)
}

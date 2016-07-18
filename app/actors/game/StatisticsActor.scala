package actors.game

import actors.game.StatisticsActor.{RequestUpdate, UpdateTask, UpdateValues}
import akka.actor.{Actor, ActorRef, Props}

import scala.collection.mutable.ArrayBuffer

/**
  * @author iRevThis
  */
final class StatisticsActor(startExp: Long, startAdena: Long, clientActor: ActorRef) extends Actor {
  val expProgression = ArrayBuffer.empty[Long]
  val adenaProgression = ArrayBuffer.empty[Long]

  override def receive: Receive = {
    case UpdateTask => clientActor ! RequestUpdate
    case UpdateValues(adena, exp) => adenaProgression += adena; expProgression += exp
  }

}

object StatisticsActor {
  case object RequestUpdate
  case object UpdateTask
  case class UpdateValues(adena: Long, exp: Long)

  def props(startExp: Long, startAdena: Long, clientActor: ActorRef) =
    Props(classOf[StatisticsActor], long2Long(startExp), long2Long(startAdena), clientActor)
}

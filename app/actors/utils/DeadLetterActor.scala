package actors.utils

import akka.actor.{Actor, DeadLetter}

/**
  * @author iRevThis
  */
final class DeadLetterActor extends Actor {
  override def receive: Receive = {
    case DeadLetter(message, _, _) => //Logger.info(s"Message from dead letter message")
  }
}

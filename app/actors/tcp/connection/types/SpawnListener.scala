package actors.tcp.connection.types

import java.util.concurrent.TimeUnit

import actors.db.types.SpawnWatcherWorker._
import actors.tcp.TcpConnectionHandler
import akka.io.Tcp
import akka.pattern._
import akka.util.Timeout
import model.prototypes.L2Npc
import network.tcp.clientpackets.highfive.Die.DieAction
import network.tcp.clientpackets.highfive.DropItem.DropItemAction
import play.Logger

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * @author iRevThis
  */
trait SpawnListener { handler: TcpConnectionHandler =>

  def validNpcId(id: Int) = (id >= 18265 && id <= 18286) || id == 27111 || id == 27113
  val npcsIds = mutable.Set.empty[Int]

  val spawnListenerToken = authInfo.token
  implicit val timeout = Timeout(10, TimeUnit.SECONDS)
  databaseManager.ask(LoadLatestNpcsIds(spawnListenerToken)).mapTo[Future[Seq[Int]]] flatMap (f => f) onSuccess {case s => npcsIds ++= s}

  def spawnListenerReceive: Receive = {
    case Tcp.Received(data) => handleNewData(data)
    case x: L2Npc if validNpcId(x.npcId) =>
      npcsIds += x.objectId
      databaseManager ! SaveNpc(spawnListenerToken , userName, user.location, NpcSaveInfo(x.objectId, x.npcId, 0, x.name, x.dead, x.location))
    case DieAction(objectId) if npcsIds.contains(objectId) => databaseManager ! SaveNpcDeath(spawnListenerToken , objectId)
    case x: DropItemAction if npcsIds.contains(x.dropperId) => databaseManager ! SaveDroppedItem(spawnListenerToken , x)
    case _: Tcp.ConnectionClosed => context stop self
  }

  override def postStop(): Unit = {
    Logger.info("Spawn listener TCP connection closed")
  }
}

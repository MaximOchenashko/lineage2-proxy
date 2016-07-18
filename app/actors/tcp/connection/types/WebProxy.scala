package actors.tcp.connection.types

import java.nio.{ByteBuffer, ByteOrder}
import java.util.concurrent.TimeUnit

import actors.db.DatabaseWorker.SavePlayer
import actors.game.StatisticsActor
import actors.game.StatisticsActor.{RequestUpdate, UpdateTask, UpdateValues}
import actors.tcp.TcpConnectionHandler
import actors.websocket.WSByNameManager.ProxyMessage
import actors.websocket.WSConnectionHandler.{NewWebSocket, TcpSocketDie}
import actors.websocket.WSManager
import actors.websocket.WSManager.WSMessage
import akka.actor.{ActorRef, Cancellable, PoisonPill}
import akka.io.{Tcp, TcpMessage}
import akka.util.ByteString
import common.utils.L2ObjectsStorage
import dto.tcp.clientpackets.{PartyUpdateAction, UpdateAction, UpdateType, WebSocketSendable}
import dto.websocket.local.AssociatedTcpSocket
import dto.websocket.receivable.{ChatMessage, ReceivableMessage}
import dto.websocket.sendable.{RestCollection, WebSocketOutMessage}
import enums.ChatType
import model.L2Creature
import model.prototypes.{L2Char, L2Npc, L2User}
import network.tcp.clientpackets.highfive.AbnormalStatusUpdate.AbnormalStatusUpdateAction
import network.tcp.clientpackets.highfive.CharMoveToLocation.CharMoveToLocationAction
import network.tcp.clientpackets.highfive.DeleteObject.DeleteObjectAction
import network.tcp.clientpackets.highfive.Die.DieAction
import network.tcp.clientpackets.highfive.PartySmallWindowAdd.PartySmallWindowAddAction
import network.tcp.clientpackets.highfive.PartySmallWindowAll.PartySmallWindowAllAction
import network.tcp.clientpackets.highfive.PartySmallWindowDelete.PartySmallWindowDeleteAction
import network.tcp.clientpackets.highfive.PartySmallWindowDeleteAll.PartySmallWindowDeleteAllAction
import network.tcp.clientpackets.highfive.PartySmallWindowUpdate.PartySmallWindowUpdateAction
import network.tcp.clientpackets.highfive.Revive.ReviveAction
import network.tcp.clientpackets.highfive.StatusUpdate.StatusUpdateAction
import network.tcp.clientpackets.highfive.ValidateLocation.ValidateLocationAction
import play.api.libs.json.{Json, JsValue, Writes}
import play.api.{Logger, Play}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.duration.Duration

/**
  * @author iRevThis
  */
//todo handle websocket die
trait WebProxy {
  handler: TcpConnectionHandler =>

  import context.system

  val taskRescheduleTime: Int = Play.current.configuration.getInt("StatUpdateTime").getOrElse(60)
  val activeWebSockets = ArrayBuffer.empty[ActorRef]
  val charsStorage = new L2ObjectsStorage[L2Char]
  val npcsStorage = new L2ObjectsStorage[L2Npc]

  var task: Cancellable = null
  var statActor: ActorRef = null

  def webProxyReceive: Receive = {
    case Tcp.Received(data) => handleNewData(data)
    case that: L2Creature => handleL2Creature(that)
    case updateAction: UpdateAction => handleUpdateAction(updateAction)
    case userUpdate: UpdateType[L2User] => userUpdate.update(user) foreach (u => user = u)
    case charUpdate: UpdateType[L2Char] =>
      charsStorage.seq(_.objectId == charUpdate.objectId)
        .map(c => charUpdate.update(c))
        .filter(_.isDefined)
        .foreach { c => charsStorage.add(c.get) }
    case x: PartyUpdateAction => handlePartyUpdate(x)
    case container: WebSocketSendable => sendMessageToWS(container) //todo move to outer?
    case NewWebSocket(tok, uName) =>
      if (token == token && uName.equalsIgnoreCase(userName)) {
        handleNewSocket(sender())
        Logger.info("New WebSocket associated")
      }
    case RequestUpdate => handleStatisticsUpdate()
    case that: ReceivableMessage => handleToClientMessage(that)
    case _: Tcp.ConnectionClosed => context stop self //todo or maybe user poison pill?
  }

  private def handleL2Creature: PartialFunction[L2Creature, Unit] = {
    case that: L2User => user.update(that)
      sendMessageToWS(that)
      databaseManager ! SavePlayer(server, that)

    case that: L2Char =>
      charsStorage.add(that)
      databaseManager ! SavePlayer(server, that)
      sendMessageToWS(that)

    case that: L2Npc =>
      npcsStorage.add(that)
      sendMessageToWS(that)
  }

  private def handleUpdateAction: PartialFunction[UpdateAction, Unit] = {
    case CharMoveToLocationAction(objectId, _, current) =>
      if (user.objectId == objectId) user = user.updateLocation(current)
      charsStorage.seq(_.objectId == objectId).map(_.updateLocation(current)).foreach(charsStorage.add)
      npcsStorage.seq(_.objectId == objectId).map(_.updateLocation(current)).foreach(npcsStorage.add)

    case DeleteObjectAction(objectId) =>
      charsStorage.delete(objectId)
      npcsStorage.delete(objectId)

    case DieAction(objectId) =>
      if (user.objectId == objectId) user = user.updateDead(true)
      charsStorage.seq(_.objectId == objectId).map(_.updateDead(true)).foreach(charsStorage.add)
      npcsStorage.seq(_.objectId == objectId).map(_.updateDead(true)).foreach(npcsStorage.add)

    case ReviveAction(objectId) =>
      if (user.objectId == objectId) user = user.updateDead(true)
      charsStorage.seq(_.objectId == objectId).map(_.updateDead(true)).foreach(charsStorage.add)
      npcsStorage.seq(_.objectId == objectId).map(_.updateDead(true)).foreach(npcsStorage.add)

    case StatusUpdateAction(objectId, attrs) =>
      if (user.objectId == objectId) user = user.updateCreature(attrs)
      charsStorage.seq(_.objectId == objectId).map(_.updateCreature(attrs)).foreach(charsStorage.add)
      npcsStorage.seq(_.objectId == objectId).map(_.updateCreature(attrs)).foreach(npcsStorage.add)

    case ValidateLocationAction(objectId, loc) =>
      if (user.objectId == objectId) user = user.updateLocation(loc)
      charsStorage.seq(_.objectId == objectId).map(_.updateLocation(loc)).foreach(charsStorage.add)
      npcsStorage.seq(_.objectId == objectId).map(_.updateLocation(loc)).foreach(npcsStorage.add)
  }

  private def handlePartyUpdate: PartialFunction[PartyUpdateAction, Unit] = {
    case PartySmallWindowAddAction(objectId, member) =>
      charsStorage.seq(_.objectId == objectId).map(_.updateChar(member)) foreach { char =>
        charsStorage.add(char)
        user.party.foreach(_.addPartyMember(char))
      }

    case PartySmallWindowAllAction(leaderObjId, loot, members) =>
      Option(user).filter(_.objectId == leaderObjId).orElse(charsStorage.get(leaderObjId)) foreach { leader =>
        val ids = members.map(_.objId)
        val chars = charsStorage.seq(v => ids.contains(v.objectId)).toBuffer
        user.createParty(leader, loot, chars)
      }

    case PartySmallWindowDeleteAction(objectId, _) =>
      user.party.foreach(_.removePartyMember(objectId))

    case PartySmallWindowDeleteAllAction =>
      user = user.copy(party = None)

    case PartySmallWindowUpdateAction(objectId, member) =>
      charsStorage.seq(_.objectId == objectId).map(_.updateChar(member)) foreach { char =>
        charsStorage.add(char)
        user.party.foreach(_.members.filter(_.objectId == objectId).foreach(_.updateChar(member)))
      }

  }

  @throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    if (task != null && !task.isCancelled)
      task.cancel()

    if (statActor != null)
      statActor ! PoisonPill.getInstance
    //todo send info that actor die to ws by selectors
    val tcpSocketDie = TcpSocketDie(userName, self)
    sendMessageToWS(tcpSocketDie)

    WSManager <<! WSMessage(token, userName, self, ProxyMessage(TcpSocketDie))

    Logger.info("WebProxy TCP connection closed")
  }

  /**
    * Handle new WebSocket. Add it to list. Send: L2User / Npc / Chars / Inventory / Effects
    *
    * @param sender WebSocket
    */
  private def handleNewSocket(sender: ActorRef): Unit = {
    sender ! new AssociatedTcpSocket(userName)

    sender ! WebSocketOutMessage(user)
    val chars = charsStorage.seq(_.location.distance(user.location) <= 2000)
    val npcs = npcsStorage.seq(_.location.distance(user.location) <= 2000)

    sender ! WebSocketOutMessage(RestCollection(chars))
    sender ! WebSocketOutMessage(RestCollection(npcs))

    if (user.effects.nonEmpty)
      sender ! WebSocketOutMessage(new AbnormalStatusUpdateAction(user.effects))
  }

  /**
    * Send message to all Web Sockets
    *
    * @param obj sendable data
    */
  //todo rewrite
  private def sendMessageToWS(obj: WebSocketSendable) {
    if (activeWebSockets.nonEmpty) {
      val message = WebSocketOutMessage(obj)(webSocketSendableWrites)
      activeWebSockets.foreach(_ ! message)
    }
  }

  private def handleToClientMessage(message: ReceivableMessage): Unit = message match {
    case that: ChatMessage =>
      val text: String = that.text
      val target: String = that.target getOrElse ""
      val chatType: ChatType = that.chatType
      val cType: Byte = ChatType.values.indexOf(chatType).toByte
      Logger.info("ChatType: " + cType + " text: " + text + " target: " + target)
      val b: ByteBuffer = ByteBuffer.allocate(text.length * 3 + target.length * 2 + 4 + 2 + 1).order(ByteOrder.BIG_ENDIAN)
      b.put(0x01.toByte)
      b.putInt(b.capacity)
      b.put(cType)
      b.put(text.getBytes("UTF-8").length.toByte)
      b.put(text.getBytes("UTF-8"))
      b.put(target.getBytes("UTF-8").length.toByte)
      b.put(target.getBytes("UTF-8"))
      Logger.info("" + b.remaining + " cap: " + b.capacity)
      Logger.info("" + Array.apply(b.array))
      remoteConnect.tell(TcpMessage.write(ByteString.fromArray(b.array)), self)
    case _ =>
  }


  /**
    * Create new StatisticsActor and refresh task for it
    */
  private def handleStartStatisticsActor(): Unit = {
    if (statActor != null) {
      Logger.warn("Trying to create new StatisticsActor, one already exists")
      return
    }

    val startAdena: Long = user.getItemCount(_.itemId == 57)
    val startExp: Long = 0L //todo dataStorage.getUser.etcUserInfo.exp
    statActor = context.actorOf(StatisticsActor.props(startExp, startAdena, self))
    task = context.system.scheduler.schedule(Duration.Zero,
      Duration(taskRescheduleTime, TimeUnit.SECONDS), statActor, UpdateTask)(context.dispatcher)
  }

  /**
    * Send new data to Statistics actors
    */
  private def handleStatisticsUpdate(): Unit = {
    if (statActor != null) {
      val currentExp: Long = 0L // todo user.etcUserInfo.exp
      val currentAdena: Long = user.getItemCount(_.itemId == 57)

      statActor ! UpdateValues(currentExp, currentAdena)
    }
  }

  private val webSocketSendableWrites: Writes[WebSocketSendable] =
    new Writes[WebSocketSendable] {
      override def writes(o: WebSocketSendable): JsValue =
        Json.parse(play.libs.Json.toJson(o).toString)
    }
}

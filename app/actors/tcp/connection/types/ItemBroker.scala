package actors.tcp.connection.types

import actors.db.DatabaseWorker.SavePlayer
import actors.db.types.BrokerDatabaseWorker.{SavePrivateStoreBuy, SavePrivateStoreSell, TraderLogout}
import actors.tcp.TcpConnectionHandler
import akka.io.Tcp
import model.prototypes.L2CharBased
import network.tcp.clientpackets.highfive.DeleteObject.DeleteObjectAction
import network.tcp.clientpackets.highfive.PrivateStoreListBuy.PrivateStoreBuyInfo
import network.tcp.clientpackets.highfive.PrivateStoreListSell.PrivateStoreSellInfo

/**
  * @author iRevThis
  */
trait ItemBroker { handler: TcpConnectionHandler =>

  def itemBrokerReceive: Receive = {
    case Tcp.Received(data) => handleNewData(data)
    case x: PrivateStoreSellInfo => databaseManager ! SavePrivateStoreSell(server, x)
    case x: PrivateStoreBuyInfo => databaseManager ! SavePrivateStoreBuy(server, x)
    case DeleteObjectAction(objId) => databaseManager ! TraderLogout(server, objId)
    case x: L2CharBased => databaseManager ! SavePlayer(server, x)
    case _: Tcp.ConnectionClosed => context stop self
  }

}

package controllers

import java.lang.management.{ManagementFactory, MemoryMXBean}
import java.util.concurrent.TimeUnit
import javax.inject.{Inject, Singleton}

import actors.tcp.TcpServerActor
import akka.actor.ActorSystem
import akka.util.Timeout
import controllers.RestController.MemoryUsage
import controllers.base.BaseController
import play.api.libs.json.Json
import play.api.mvc.Action
import services.ItemDataService
import services.security.AuthService

import scala.concurrent.{ExecutionContext, Future}

/**
  * @author iRevThis
  */
@Singleton
class RestController @Inject()(val authService: AuthService, itemDataService: ItemDataService)
                              (implicit system: ActorSystem, ec: ExecutionContext) extends BaseController {

  implicit val timeout = Timeout(10, TimeUnit.SECONDS)
  implicit val serverUsageFormat = Json.format[MemoryUsage]

  private val memMXbean: MemoryMXBean = ManagementFactory.getMemoryMXBean

  def getConnectionsNames = authorized().async { implicit request =>
    request.keys.xApiToken match {
      case Some(t) => TcpServerActor.tcpConnectionNamesByToken(t) map (ok(_)) recover { case _ => ok(Seq.empty[String]) }
      case None => Future.successful(Unauthorized)
    }
  }

  def memoryUsage = Action {
    val maxMem: Double = memMXbean.getHeapMemoryUsage.getMax / 1024f
    val allocatedMem: Double = memMXbean.getHeapMemoryUsage.getCommitted / 1024f
    val usedMem: Double = memMXbean.getHeapMemoryUsage.getUsed / 1024f
    val nonAllocatedMem: Double = maxMem - allocatedMem
    val cachedMem: Double = allocatedMem - usedMem
    val usableMem: Double = maxMem - usedMem

    val allocatedMemPer = mem2percent(allocatedMem, maxMem)
    val nonAllocatedMemPer = mem2percent(nonAllocatedMem, maxMem)
    val usedMemPer = mem2percent(usedMem, maxMem)
    val cachedMemPer = mem2percent(cachedMem, maxMem)
    val usableMemPer = mem2percent(usableMem, maxMem)
    val usage = MemoryUsage(
      maxMem,
      allocatedMem,
      allocatedMemPer,
      usedMem,
      usedMemPer,
      nonAllocatedMem,
      nonAllocatedMemPer,
      cachedMem,
      cachedMemPer,
      usableMem,
      usableMemPer
    )

    Ok(Json.prettyPrint(Json.toJson(usage)))
  }

  private def mem2percent(mem: Double, maxMem: Double) = mem / maxMem * 1000000.toDouble / 10000
}

object RestController {

  case class MemoryUsage(maxMem: Double,
                         allocatedMem: Double, allocatedMemPer: Double,
                         usedMem: Double, usedMemPer: Double,
                         nonAllocatedMem: Double, nonAllocatedMemPer: Double,
                         cachedMem: Double, cachedMemPer: Double,
                         usableMem: Double, usableMemPer: Double)

}

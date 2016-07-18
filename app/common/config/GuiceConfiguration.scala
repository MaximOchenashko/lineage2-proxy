package common.config

import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport
import services.ServerLoader

/**
 * @author iRevThis
 */
class GuiceConfiguration extends AbstractModule with AkkaGuiceSupport {

  override def configure(): Unit = {
    bind(classOf[ServerLoader]).asEagerSingleton()
    bind(classOf[JavaJacksonConfiguration]).asEagerSingleton()
  }

}

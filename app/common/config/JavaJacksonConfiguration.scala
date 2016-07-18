package common.config

import javax.inject.Singleton

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import play.libs.Json

/**
  * @author iRevThis
  */
@Singleton
class JavaJacksonConfiguration {

  val mapper = new ObjectMapper()
    .registerModule(DefaultScalaModule)

  Json.setObjectMapper(mapper)

}

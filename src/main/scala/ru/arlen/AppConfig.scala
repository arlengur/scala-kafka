package ru.arlen

import pureconfig.ConfigReader
import pureconfig.generic.semiauto._
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

case class AppConfigKafka(
    bootstrap: String,
    topic: String,
    keySerializer: String,
    valueSerializer: String,
    keyDeserializer: String,
    valueDeserializer: String,
    groupId: String
)

object AppConfigKafka {
  private lazy val conf: Config =
    ConfigFactory.load("application.conf").getConfig("kafka")

  def apply(): AppConfigKafka = {
    AppConfigKafka(
      bootstrap = conf.getString("bootstrap.servers"),
      topic = conf.getString("topic"),
      keySerializer = conf.getString("key.serializer"),
      valueSerializer = conf.getString("value.serializer"),
      keyDeserializer = conf.getString("key.deserializer"),
      valueDeserializer = conf.getString("value.deserializer"),
      groupId = conf.getString("group.id")
    )
  }
}

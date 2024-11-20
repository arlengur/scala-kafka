package ru.arlen.config

import com.typesafe.config.Config

import scala.jdk.CollectionConverters._

object ConfigUtils {
  implicit class ConfigOps(config: Config) {
    def getConfigObjectAsMap(path: String): Map[String, Object] =
      config
        .getConfig(path)
        .entrySet()
        .asScala
        .map(entry => (entry.getKey, entry.getValue.unwrapped().toString))
        .toMap
  }
}

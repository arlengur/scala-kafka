package ru.arlen

import com.typesafe.config.{Config, ConfigFactory}

import scala.jdk.CollectionConverters.CollectionHasAsScala

package object conf {
  implicit class ConfigOps(config: Config) {
    def getConfigObjectAsMap(path: String): java.util.Map[String, Object] = {
      val map = new java.util.HashMap[String, Object]

      config
        .getConfig(path)
        .entrySet()
        .asScala
        .foreach(entry => map.put(entry.getKey, entry.getValue.unwrapped()))
      map
    }
  }

  lazy val root: Config = ConfigFactory.load()
}

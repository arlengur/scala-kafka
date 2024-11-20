package ru.arlen.config

case class RecorderConf(
    kafkaProps: Map[String, Object],
    topics: List[String]
)

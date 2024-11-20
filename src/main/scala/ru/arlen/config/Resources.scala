package ru.arlen.config

import com.typesafe.config.ConfigFactory
import ru.arlen.config.ConfigUtils.ConfigOps

import java.time.Duration

object Resources {
  // message-recorder
  private def recorderConf = ConfigFactory.load().getConfig("recorder")
  private def kafka        = recorderConf.getConfig("kafka")

  def kafkaTopic = kafka.getString("topic")

  def kafkaConsumerConfigMap           = kafka.getConfigObjectAsMap("consumer.kafka-clients")
  val processingBatchSize: Int         = 1000
  val processingBatchTimeout: Duration = Duration.ofSeconds(1)

  // post-processing
  def producerConf = ConfigFactory.load().getConfig("post-processing.writer")
  def kafkaProps   = producerConf.getConfigObjectAsMap("kafka-clients")
  def writerTopic  = producerConf.getString("topic")

}

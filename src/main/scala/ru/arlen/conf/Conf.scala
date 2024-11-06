package ru.arlen.conf

object Conf {
  object PostProcessing {
    def writerProducerConfig = root.getConfig("post-processing.writer")
    def kafkaProps           = writerProducerConfig.getConfigObjectAsMap("kafka-clients")

    def writerTopic = writerProducerConfig.getString("topic")
  }

}

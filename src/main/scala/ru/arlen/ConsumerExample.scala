package ru.arlen

import java.util
import java.util.Properties
import org.apache.kafka.clients.consumer.KafkaConsumer

import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.ConsumerConfig._

object ConsumerExample extends App {
  val kafkaConf = AppConfigKafka()

  val props = new Properties()
  props.put(BOOTSTRAP_SERVERS_CONFIG, kafkaConf.bootstrap)
  props.put(KEY_DESERIALIZER_CLASS_CONFIG, kafkaConf.keyDeserializer)
  props.put(VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConf.valueDeserializer)
  props.put(GROUP_ID_CONFIG, kafkaConf.groupId)
  props.put(AUTO_OFFSET_RESET_CONFIG, "earliest")

  val consumer = new KafkaConsumer[String, String](props)

  consumer.subscribe(util.Collections.singletonList(kafkaConf.topic))

  while (true) {
    val records = consumer.poll(100)
    for (record <- records.asScala) {
      println(record.key + "->" + record.value)
    }
  }
}

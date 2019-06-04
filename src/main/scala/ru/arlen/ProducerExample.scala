package ru.arlen

import java.util.{Date, Properties}

import org.apache.kafka.clients.producer._
import org.apache.kafka.clients.producer.ProducerConfig._
import com.typesafe.config.ConfigFactory

object ProducerExample extends App {
  val kafkaConf = AppConfigKafka()

  val props = new Properties()
  props.put(BOOTSTRAP_SERVERS_CONFIG, kafkaConf.bootstrap)
  props.put(KEY_SERIALIZER_CLASS_CONFIG, kafkaConf.keySerializer)
  props.put(VALUE_SERIALIZER_CLASS_CONFIG, kafkaConf.valueSerializer)

  val producer = new KafkaProducer[String, String](props)
  val record           = new ProducerRecord[String, String](kafkaConf.topic, "key", """{"item":"book", "price":10.99}""")
  producer.send(record)
  producer.close
}

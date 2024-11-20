package ru.arlen.postprocessing

import com.typesafe.scalalogging.StrictLogging
import io.circe.Json
import io.circe.generic.auto._
import io.circe.syntax._
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.serialization.StringSerializer
import ru.arlen.config.Resources
import ru.arlen.postprocessing.model.PostProcessingCommand

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

class PostProcessingWriter[K, V](producer: KafkaProducer[K, V]) extends StrictLogging with AutoCloseable {
  private final val loggingCallback: Callback = (meta: RecordMetadata, e: Exception) =>
    if (e != null) {
      logger.error(s"Writer post_processing failed to write {}", Option(meta).toString, e.toString)
    }

  final def send(record: ProducerRecord[K, V], errorMessage: Option[String] = None): Unit =
    Try(producer.send(record, loggingCallback)) match {
      case Failure(e) =>
        logger.error(s"Writer post_processing failed to write to topic ${record.topic()}", errorMessage, e.getMessage)
      case Success(_) =>
        producer.flush()
    }

  final def close(): Unit = producer.close()
}

object Test extends App {
  val topic: String                        = Resources.writerTopic
  val props: java.util.Map[String, Object] = Resources.kafkaProps.asJava

  val cmd      = PostProcessingCommand("123", 0, "20", "234", "agent_info", Array(Json.fromInt(1)), None)
  val producer = new KafkaProducer[String, Json](props, new StringSerializer, new JsonSerializer)
  val writer   = new PostProcessingWriter(producer)
  val record   = new ProducerRecord[String, Json](topic, cmd.asJson)

  println(cmd.asJson.noSpaces)
//  writer.send(record)

}

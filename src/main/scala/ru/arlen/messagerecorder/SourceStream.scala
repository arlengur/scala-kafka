package ru.arlen.messagerecorder

import ru.arlen.config.RecorderConf
import zio.kafka.consumer.{Consumer, Offset, Subscription}
import zio.kafka.serde.Serde
import zio.stream.Stream
import zio.{Console, ZIO, ZLayer, _}

trait SourceStream[Context] {
  def stream: Stream[Throwable, Context]
}

class KafkaSourceImpl(consumer: Consumer, kafkaConf: RecorderConf) extends SourceStream[Offset] {
  override def stream: Stream[Throwable, Offset] =
    consumer
      .plainStream(
        Subscription.topics(kafkaConf.topics.head, kafkaConf.topics.tail: _*),
        Serde.string,
        Serde.string,
        5
      )
      .tap(r => Console.printLine(r.record.value()).as(r))
      .tap(_ => ZIO.sleep(2.seconds))
      .map(_.offset)
}

object KafkaSourceImpl {
  val layer = ZLayer {
    for {
      consumer     <- ZIO.service[Consumer]
      recorderConf <- ZIO.service[RecorderConf]
    } yield new KafkaSourceImpl(consumer, recorderConf)
  }
}

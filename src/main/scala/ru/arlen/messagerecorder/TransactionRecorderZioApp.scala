package ru.arlen.messagerecorder

import ru.arlen.config.{RecorderConf, Resources}
import zio.kafka.consumer.diagnostics.Diagnostics
import zio.kafka.consumer.{Consumer, ConsumerSettings, Offset}
import zio.logging.backend.SLF4J
import zio.stream.ZSink
import zio.{RLayer, Runtime, Schedule, ZIO, ZIOAppDefault, ZLayer, _}

object TransactionRecorderZioApp extends ZIOAppDefault {

  val consumerSettingLayer: RLayer[RecorderConf, ConsumerSettings] =
    ZLayer {
      for {
        conf <- ZIO.service[RecorderConf]
        consumerSettings = ConsumerSettings().withProperties(conf.kafkaProps)
      } yield consumerSettings
    }

  val app = for {
    source       <- ZIO.service[SourceStream[Offset]]
    businessFlow <- ZIO.service[BusinessFlow[Offset]]

    _ <- source.stream
      .via(businessFlow.flow)
      .run(ZSink.drain)
      .tapError(ex => ZIO.logError(ex.toString))
      .retry(Schedule.exponential(1.second))
  } yield ()

  override val bootstrap = Runtime.removeDefaultLoggers >>> SLF4J.slf4j

  override def run: ZIO[Any, Throwable, Unit] =
    app.provide(
      ZLayer.succeed(RecorderConf(Resources.kafkaConsumerConfigMap, List(Resources.kafkaTopic))),
      ZLayer.succeed(Diagnostics.NoOp),
      KafkaSourceImpl.layer,
      consumerSettingLayer,
      Consumer.live,
      BusinessFlowImpl.layer
    )
}

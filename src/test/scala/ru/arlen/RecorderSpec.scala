package ru.arlen

import ru.arlen.messagerecorder.{BusinessFlow, BusinessFlowImpl}
import zio.kafka.consumer.{Consumer, Offset, Subscription}
import zio.kafka.serde.Serde
import zio.kafka.testkit.KafkaTestUtils.{consumer, produceMany, producer}
import zio.kafka.testkit.{Kafka, KafkaRandom}
import zio.stream.ZSink
import zio.test.{Assertion, Spec, TestAspect, TestClock, TestEnvironment, ZIOSpecDefault, assert, assertCompletes}
import zio.{Scope, _}

object RecorderSpec extends ZIOSpecDefault with KafkaRandom {
  override def kafkaPrefix: String = "consumer-spec"
  override def spec: Spec[TestEnvironment with Scope, Any] =
    suite("Check Kafka stream") {
      test("Retry stream in fail case") {
        val kvs: List[(String, String)] = (1 to 5).toList.map(i => (s"key-$i", s"msg-$i"))
        for {
          counter <- Ref.make(0)

          topic  <- randomTopic
          client <- randomClient
          group  <- randomGroup

          _ <- produceMany(topic, kvs)

          businessFlow <- ZIO.service[BusinessFlow[Offset]]

          _ <- Consumer
            .plainStream(
              Subscription.Topics(Set(topic)),
              Serde.string,
              Serde.string,
              5
            )
            .tap(_ => counter.update(_ + 1) *> ZIO.sleep(5.second))
            .map(_.offset)
            .via(businessFlow.flow)
            .run(ZSink.drain)
            .tapError(ex => ZIO.logError(ex.toString))
            .retry(Schedule.exponential(1.second))
            .provideSome[Kafka](
              consumer(clientId = client, groupId = Some(group), properties = Map("max.poll.interval.ms" -> "100"))
            )
            .fork
          _     <- TestClock.adjust(1.minute)
          count <- counter.get
        } yield assertCompletes && assert(count)(Assertion.isGreaterThan(5))
      }
    }.provideShared(
      producer,
      Kafka.embedded,
      BusinessFlowImpl.layer
    ) @@ TestAspect.timeout(1.minute)
}

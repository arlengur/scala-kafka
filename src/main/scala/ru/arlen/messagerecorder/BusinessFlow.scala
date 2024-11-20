package ru.arlen.messagerecorder

import zio.kafka.consumer.{Consumer, Offset, OffsetBatch}
import zio.stream.ZPipeline
import zio.{ULayer, ZLayer}

trait BusinessFlow[Context] {
  def flow: ZPipeline[Any, Throwable, Context, Unit]
}

class BusinessFlowImpl extends BusinessFlow[Offset] {

  def flow: ZPipeline[Any, Throwable, Offset, Unit] = offsetBatch >>> commit

  def offsetBatch: ZPipeline[Any, Throwable, Offset, OffsetBatch] =
    ZPipeline.aggregateAsync(Consumer.offsetBatches)

  def commit: ZPipeline[Any, Throwable, OffsetBatch, Unit] =
    ZPipeline.mapZIO[Any, Throwable, OffsetBatch, Unit](_.commit)
}

object BusinessFlowImpl {
  val layer: ULayer[BusinessFlowImpl] = ZLayer.succeed(new BusinessFlowImpl)
}

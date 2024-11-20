package ru.arlen.postprocessing.model

import io.circe.Json
import io.circe.generic.extras.{Configuration, ConfiguredJsonCodec}
import io.circe.syntax.EncoderOps

@ConfiguredJsonCodec
case class Message(
    orgId: Int,
    messageTypeId: String,
    messageId: String,
    unixTimestampMs: Long,
    timezoneId: String,
    payload: Map[String, Json]
)

object Message {
  implicit val config: Configuration = Configuration.default

  def toJson(tx: Message): Json = tx.asJson

}

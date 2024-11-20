package ru.arlen.postprocessing

import io.circe.Json
import io.circe.jackson.jacksonPrintByteBuffer
import org.apache.kafka.common.serialization.Serializer

class JsonSerializer extends Serializer[Json] {

  def serialize(topic: String, data: Json): Array[Byte] = {
    val buffer = jacksonPrintByteBuffer(data)
    val bytes  = new Array[Byte](buffer.limit)
    buffer.get(bytes, 0, buffer.limit)
    bytes
  }
}

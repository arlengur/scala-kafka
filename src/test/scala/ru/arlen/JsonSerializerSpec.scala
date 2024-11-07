package ru.arlen


import io.circe.syntax._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers

class JsonSerializerSpec extends AnyFreeSpec with Matchers {
  "Common json serializer" - {

    val serializer = new JsonSerializer
    val topic      = "topic-name"

    "Serialize long message" in {
      val json = Map(
        "someField" -> "someValue".asJson,
        "someObject" -> Map(
          "someInnerField" -> "someInnerValue",
          "longField"      -> "a" * 10000,
        ).asJson,
      ).asJson

      serializer.serialize(topic, json) should be(json.toString.getBytes)
    }

    "Serialize long message with xml unicode sequences" in {
      val json = Map(
        "someField" -> "someValue".asJson,
        "someObject" -> Map(
          "someInnerField"      -> "someInnerValue",
          "longXmlUnicodeField" -> "&#x1f493;&#x1f493;&#x1f495;&#x1f495;&#x1f496;&#x1f496;&#x1f49c;&#x1f49c;❤❤" * 10000,
        ).asJson,
      ).asJson

      serializer.serialize(topic, json) should be(json.toString.getBytes)
    }

    "Serialize long message with unicode" in {
      val json = Map(
        "someField" -> "someValue".asJson,
        "someObject" -> Map(
          "someInnerField" -> "someInnerValue",
          "longUnicodeField" -> "\uD83D\uDC93\uD83D\uDC93\uD83D\uDC95\uD83D\uDC95\uD83D\uDC96\uD83D\uDC96\uD83D\uDC9C\uD83D\uDC9C❤❤" * 10000,
        ).asJson,
      ).asJson

      serializer.serialize(topic, json) should be(json.toString.getBytes)
    }
  }
}


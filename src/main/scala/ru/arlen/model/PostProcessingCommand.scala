package ru.arlen.model

import io.circe.Json

import java.time.Instant
import java.util.UUID

case class PostProcessingCommand(
    commandId: String = UUID.randomUUID().toString,
    sourceOrgId: Int,
    sourceMessageTypeId: String,
    sourceMessageId: String,
    command: String,
    arguments: Array[Json],
    scheduledIsoTimestamp: Option[Instant]
)
package dev.cankolay.trash.server.module.connection.mapper

import dev.cankolay.trash.server.module.application.mapper.toDto
import dev.cankolay.trash.server.module.auth.mapper.toDto
import dev.cankolay.trash.server.module.connection.dto.ConnectionDto
import dev.cankolay.trash.server.module.connection.entity.Connection

fun Connection.toDto() = ConnectionDto(
    application = application.toDto(),
    token = token.toDto(),
    createdAt = createdAt
)

package dev.cankolay.trash.server.module.connection.dto

import dev.cankolay.trash.server.module.application.dto.ApplicationDto
import dev.cankolay.trash.server.module.auth.dto.TokenDto
import java.time.Instant

data class ConnectionDto(
    val token: TokenDto,
    val application: ApplicationDto,

    val createdAt: Instant?,
)

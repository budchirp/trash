package dev.cankolay.trash.server.module.connection.dto

import dev.cankolay.trash.server.module.application.dto.ApplicationDto
import dev.cankolay.trash.server.module.auth.dto.TokenDto

data class ConnectionDto(
    val id: Long,
    val application: ApplicationDto,
    val token: TokenDto
)

package dev.cankolay.trash.server.module.session.dto

import dev.cankolay.trash.server.common.model.UserAgentPlatform
import dev.cankolay.trash.server.module.auth.dto.TokenDto
import java.time.Instant

data class SessionDtoDevice(
    val name: String,
    val platform: UserAgentPlatform,
    val os: String,
)

data class SessionDto(
    val token: TokenDto,

    val ip: String,
    val browser: String,

    val device: SessionDtoDevice,

    val createdAt: Instant?,
)

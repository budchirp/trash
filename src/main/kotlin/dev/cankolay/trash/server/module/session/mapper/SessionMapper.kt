package dev.cankolay.trash.server.module.session.mapper

import dev.cankolay.trash.server.module.auth.mapper.toDto
import dev.cankolay.trash.server.module.session.dto.SessionDto
import dev.cankolay.trash.server.module.session.dto.SessionDtoDevice
import dev.cankolay.trash.server.module.session.entity.Session

fun Session.toDto() = SessionDto(
    token = token.toDto(),
    ip = ip,
    browser = browser,
    device = SessionDtoDevice(
        name = device,
        platform = platform,
        os = os
    ),
    createdAt = createdAt
)

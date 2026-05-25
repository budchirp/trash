package dev.cankolay.trash.server.module.auth.mapper

import dev.cankolay.trash.server.module.auth.dto.TokenDto
import dev.cankolay.trash.server.module.auth.entity.Token

fun Token.toDto() = TokenDto(
    id = id,
    expiresAt = expiresAt,
    permissions = permissions.sorted()
)

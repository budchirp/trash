package dev.cankolay.trash.server.module.auth.dto

import dev.cankolay.trash.server.module.security.dto.PermissionDto

data class TokenDto(
    val id: String,
    val expiresAt: String,
    val permissions: List<PermissionDto>
)
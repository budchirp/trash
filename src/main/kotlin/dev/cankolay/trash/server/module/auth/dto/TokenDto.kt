package dev.cankolay.trash.server.module.auth.dto

import java.time.Instant

data class TokenDto(
    val id: String,
    val expiresAt: Instant,
    val permissions: List<String>
)

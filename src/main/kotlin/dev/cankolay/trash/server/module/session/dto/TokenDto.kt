package dev.cankolay.trash.server.module.session.dto

data class TokenDto(
    val token: String,
    val expiresAt: String
)

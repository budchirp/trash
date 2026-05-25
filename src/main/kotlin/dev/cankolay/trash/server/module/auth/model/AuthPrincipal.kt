package dev.cankolay.trash.server.module.auth.model

import dev.cankolay.trash.server.module.auth.entity.TokenType

data class AuthPrincipal(
    val user: String,
    val token: String,
    val type: TokenType,
    val permissions: Set<String>
)

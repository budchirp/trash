package dev.cankolay.trash.server.module.session.dto.request

data class CreateSessionRequestDto(
    val email: String,
    val password: String
)

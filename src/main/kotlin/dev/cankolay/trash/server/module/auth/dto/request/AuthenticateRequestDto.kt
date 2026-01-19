package dev.cankolay.trash.server.module.auth.dto.request

data class AuthenticateRequestDto(
    val applicationId: String,
    val callbackUrl: String,
    val permissions: Set<String>
)

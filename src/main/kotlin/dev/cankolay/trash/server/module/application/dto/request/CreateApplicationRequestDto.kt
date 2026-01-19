package dev.cankolay.trash.server.module.application.dto.request

data class CreateApplicationRequestDto(
    val name: String,
    val callbackUrl: String
)

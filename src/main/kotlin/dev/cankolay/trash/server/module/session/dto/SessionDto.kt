package dev.cankolay.trash.server.module.session.dto

data class SessionDtoDevice(
    val name: String,
    val platform: String,
    val os: String,
)

data class SessionDto(
    val user: String,
    val token: TokenDto,

    val ip: String,
    val browser: String,

    val device: SessionDtoDevice,

    val expiresAt: String
)

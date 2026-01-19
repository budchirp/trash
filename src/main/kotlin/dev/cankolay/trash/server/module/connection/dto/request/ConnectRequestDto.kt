package dev.cankolay.trash.server.module.connection.dto.request

data class ConnectRequestDto(
    val applicationId: String,
    val callback: String,
    val permissions: Set<String>
)

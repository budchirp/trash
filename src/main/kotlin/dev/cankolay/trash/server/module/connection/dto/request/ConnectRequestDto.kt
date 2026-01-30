package dev.cankolay.trash.server.module.connection.dto.request

data class ConnectRequestDto(
    val application_id: String,
    val permissions: Set<String>
)

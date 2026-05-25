package dev.cankolay.trash.server.module.connection.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class ConnectRequestDto(
    @field:NotBlank
    val applicationId: String,

    @field:NotEmpty
    val permissions: Set<String>
)

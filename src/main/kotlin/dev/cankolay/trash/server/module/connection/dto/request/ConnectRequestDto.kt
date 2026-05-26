package dev.cankolay.trash.server.module.connection.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class ConnectRequestDto(
    @param:JsonProperty("applicationId")
    @field:NotBlank
    val applicationId: String,

    @field:NotEmpty
    val permissions: Set<String>
)

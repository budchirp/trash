package dev.cankolay.trash.server.module.security.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateSecurityTokenRequestDto(
    @field:NotBlank
    @field:Size(min = 8, max = 128)
    val password: String
)

package dev.cankolay.trash.server.module.security.dto.request

import jakarta.validation.constraints.NotBlank

data class VerifySecurityTokenRequestDto(
    @field:NotBlank
    val token: String,
)

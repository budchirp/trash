package dev.cankolay.trash.server.module.user.dto.request

import jakarta.validation.constraints.NotBlank

data class DeleteUserRequestDto(
    @field:NotBlank
    val token: String
)

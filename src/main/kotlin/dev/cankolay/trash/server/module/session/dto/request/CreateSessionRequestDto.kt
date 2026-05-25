package dev.cankolay.trash.server.module.session.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateSessionRequestDto(
    @field:Email
    @field:NotBlank
    @field:Size(max = 255)
    val email: String,

    @field:NotBlank
    @field:Size(min = 8, max = 128)
    val password: String
)

package dev.cankolay.trash.server.module.user.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateUserRequestDto(
    @field:Email
    @field:NotBlank
    @field:Size(max = 255)
    val email: String,

    @field:NotBlank
    @field:Size(min = 3, max = 50)
    val username: String,

    @field:NotBlank
    @field:Size(min = 8, max = 128)
    val password: String
)

package dev.cankolay.trash.server.module.security.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.web.ApiResponseFactory
import dev.cankolay.trash.server.module.security.dto.request.CreateSecurityTokenRequestDto
import dev.cankolay.trash.server.module.security.dto.request.VerifySecurityTokenRequestDto
import dev.cankolay.trash.server.module.security.dto.response.CreateSecurityTokenResponseDto
import dev.cankolay.trash.server.module.security.service.SecurityTokenService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/security/token")
class SecurityTokenController(
    private val responses: ApiResponseFactory,
    private val securityTokenService: SecurityTokenService,
) {
    @PostMapping
    fun create(@Valid @RequestBody body: CreateSecurityTokenRequestDto): ResponseEntity<ApiResponse<CreateSecurityTokenResponseDto>> =
        responses.ok(
            data = CreateSecurityTokenResponseDto(
                token = securityTokenService.create(password = body.password)
            )
        )

    @PostMapping("/verify")
    fun verify(@Valid @RequestBody body: VerifySecurityTokenRequestDto): ResponseEntity<ApiResponse<Nothing>> {
        securityTokenService.verify(jwt = body.token)

        return responses.ok()
    }
}

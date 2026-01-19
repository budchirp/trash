package dev.cankolay.trash.server.module.security.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.service.I18nService
import dev.cankolay.trash.server.common.util.Controller
import dev.cankolay.trash.server.module.auth.annotation.Authenticate
import dev.cankolay.trash.server.module.security.dto.request.CreateSecurityTokenRequestDto
import dev.cankolay.trash.server.module.security.dto.request.VerifySecurityTokenRequestDto
import dev.cankolay.trash.server.module.security.dto.response.CreateSecurityTokenResponseDto
import dev.cankolay.trash.server.module.security.service.SecurityTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/security/token")
class SecurityTokenController(
    private val controller: Controller,
    private val i18nService: I18nService,
    private val securityTokenService: SecurityTokenService,
) {
    @Authenticate
    @PostMapping
    fun create(@RequestBody body: CreateSecurityTokenRequestDto): ResponseEntity<ApiResponse<CreateSecurityTokenResponseDto>> =
        controller {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = CreateSecurityTokenResponseDto(
                        token = securityTokenService.create(password = body.password)
                    )
                )
            )
        }

    @Authenticate
    @PostMapping("/verify")
    fun verify(@RequestBody body: VerifySecurityTokenRequestDto): ResponseEntity<ApiResponse<Nothing>> =
        controller {
            securityTokenService.verify(jwt = body.token)

            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success"
                )
            )
        }
}
package dev.cankolay.trash.server.module.auth.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.util.Controller
import dev.cankolay.trash.server.module.auth.annotation.Authenticate
import dev.cankolay.trash.server.module.auth.dto.request.AuthenticateRequestDto
import dev.cankolay.trash.server.module.auth.service.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping
class AuthenticationController(
    private val controller: Controller,
    private val authenticationService: AuthenticationService
) {
    @Authenticate
    @PostMapping("/authenticate")
    fun authenticate(@RequestBody body: AuthenticateRequestDto): ResponseEntity<ApiResponse<Nothing?>> =
        controller {
            val redirectUrl = authenticationService.authenticate(
                applicationId = body.applicationId,
                callback = body.callbackUrl,
                permissions = body.permissions
            )

            ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectUrl))
                .build()
        }
}

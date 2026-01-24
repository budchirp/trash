package dev.cankolay.trash.server.module.session.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.service.I18nService
import dev.cankolay.trash.server.common.service.RequestInfoService
import dev.cankolay.trash.server.common.util.Controller
import dev.cankolay.trash.server.common.util.UserAgentParser
import dev.cankolay.trash.server.module.auth.annotation.Authenticate
import dev.cankolay.trash.server.module.session.dto.SessionDto
import dev.cankolay.trash.server.module.session.dto.request.CreateSessionRequestDto
import dev.cankolay.trash.server.module.session.dto.response.CreateSessionResponseDto
import dev.cankolay.trash.server.module.session.entity.toDto
import dev.cankolay.trash.server.module.session.service.SessionService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/session")
class SessionController(
    private val controller: Controller,
    private val i18nService: I18nService,
    private val sessionService: SessionService,
    private val requestInfoService: RequestInfoService,
    private val userAgentParser: UserAgentParser
) {
    @PostMapping
    fun create(
        request: HttpServletRequest,
        @RequestBody body: CreateSessionRequestDto
    ): ResponseEntity<ApiResponse<CreateSessionResponseDto>> =
        controller {
            val userAgent = requestInfoService.getUserAgent(request)
            val ip = requestInfoService.getClientIp(request)

            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = CreateSessionResponseDto(
                        token = sessionService.create(
                            userAgent = userAgentParser(userAgent),
                            ip = ip,
                            email = body.email,
                            password = body.password
                        )
                    )
                )
            )
        }

    @Authenticate
    @GetMapping("/all")
    fun getAll(): ResponseEntity<ApiResponse<List<SessionDto>>> =
        controller(permissions = listOf("session:read")) {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = sessionService.getAll().map { it.toDto() }
                )
            )
        }

    @Authenticate
    @GetMapping("/{token}")
    fun get(@PathVariable token: String): ResponseEntity<ApiResponse<SessionDto>> =
        controller(permissions = listOf("session:read")) {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = sessionService.get(tokenId = token).toDto()
                )
            )
        }

    @Authenticate
    @GetMapping
    fun get(): ResponseEntity<ApiResponse<SessionDto>> =
        controller(permissions = listOf("session:read")) {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = sessionService.get().toDto()
                )
            )
        }

    @Authenticate
    @DeleteMapping
    fun delete(): ResponseEntity<ApiResponse<Nothing>> =
        controller(permissions = listOf("session:delete")) {
            sessionService.delete()

            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success"
                )
            )
        }

    @Authenticate
    @DeleteMapping("/{token}")
    fun delete(@PathVariable token: String): ResponseEntity<ApiResponse<Nothing>> =
        controller(permissions = listOf("session:delete")) {
            sessionService.delete(tokenId = token)

            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success"
                )
            )
        }
}
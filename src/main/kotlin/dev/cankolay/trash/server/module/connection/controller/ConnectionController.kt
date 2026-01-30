package dev.cankolay.trash.server.module.connection.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.service.I18nService
import dev.cankolay.trash.server.common.util.Controller
import dev.cankolay.trash.server.module.auth.annotation.Authenticate
import dev.cankolay.trash.server.module.connection.dto.ConnectionDto
import dev.cankolay.trash.server.module.connection.dto.request.ConnectRequestDto
import dev.cankolay.trash.server.module.connection.dto.response.ConnectResponseDto
import dev.cankolay.trash.server.module.connection.entity.toDto
import dev.cankolay.trash.server.module.connection.service.ConnectionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/connection")
class ConnectionController(
    private val controller: Controller,
    private val i18nService: I18nService,
    private val connectionService: ConnectionService
) {
    @Authenticate
    @PostMapping("/connect")
    fun connect(@RequestBody body: ConnectRequestDto): ResponseEntity<ApiResponse<ConnectResponseDto>> =
        controller(permissions = listOf("connection:create")) {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = ConnectResponseDto(
                        token = connectionService.connect(
                            applicationId = body.application_id,
                            permissions = body.permissions.toList()
                        )
                    )
                )
            )
        }

    @Authenticate
    @GetMapping("/all")
    fun getAll(): ResponseEntity<ApiResponse<List<ConnectionDto>>> =
        controller(permissions = listOf("connection:read")) {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = connectionService.getAll().map { it.toDto() }
                )
            )
        }

    @Authenticate
    @GetMapping("/{token}")
    fun get(@PathVariable token: String): ResponseEntity<ApiResponse<ConnectionDto>> =
        controller(permissions = listOf("connection:read")) {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = connectionService.get(tokenId = token).toDto()
                )
            )
        }

    @Authenticate
    @DeleteMapping("/{token}")
    fun delete(@PathVariable token: String): ResponseEntity<ApiResponse<Nothing>> =
        controller(permissions = listOf("connection:delete")) {
            connectionService.delete(tokenId = token)

            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success"
                )
            )
        }
}

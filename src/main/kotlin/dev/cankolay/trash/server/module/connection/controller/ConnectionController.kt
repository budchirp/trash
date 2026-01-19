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
    fun connect(@RequestBody body: ConnectRequestDto): ResponseEntity<ApiResponse<ConnectResponseDto?>> =
        controller {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = ConnectResponseDto(
                        redirect = connectionService.connect(
                            applicationId = body.applicationId,
                            callback = body.callback,
                            permissions = body.permissions
                        )
                    )
                )
            )
        }

    @Authenticate
    @GetMapping("/all")
    fun getAll(): ResponseEntity<ApiResponse<List<ConnectionDto>>> =
        controller {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = connectionService.getAll().map { it.toDto() }
                )
            )
        }

    @Authenticate
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<ApiResponse<ConnectionDto>> =
        controller {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = connectionService.get(id).toDto()
                )
            )
        }

    @Authenticate
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse<Nothing>> =
        controller {
            connectionService.delete(id)

            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success"
                )
            )
        }
}

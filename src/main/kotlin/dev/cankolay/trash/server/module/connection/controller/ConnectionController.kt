package dev.cankolay.trash.server.module.connection.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.web.ApiResponseFactory
import dev.cankolay.trash.server.module.connection.dto.ConnectionDto
import dev.cankolay.trash.server.module.connection.dto.request.ConnectRequestDto
import dev.cankolay.trash.server.module.connection.dto.response.ConnectResponseDto
import dev.cankolay.trash.server.module.connection.mapper.toDto
import dev.cankolay.trash.server.module.connection.service.ConnectionService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/connection")
class ConnectionController(
    private val responses: ApiResponseFactory,
    private val connectionService: ConnectionService
) {
    @PostMapping("/connect")
    fun connect(@Valid @RequestBody body: ConnectRequestDto): ResponseEntity<ApiResponse<ConnectResponseDto>> =
        responses.ok(
            data = ConnectResponseDto(
                token = connectionService.connect(
                    applicationId = body.applicationId,
                    permissions = body.permissions
                )
            )
        )

    @GetMapping("/all")
    fun getAll(): ResponseEntity<ApiResponse<List<ConnectionDto>>> =
        responses.ok(data = connectionService.getAll().map { it.toDto() })

    @GetMapping("/{token}")
    fun get(@PathVariable token: String): ResponseEntity<ApiResponse<ConnectionDto>> =
        responses.ok(data = connectionService.get(tokenId = token).toDto())

    @DeleteMapping("/{token}")
    fun delete(@PathVariable token: String): ResponseEntity<ApiResponse<Nothing>> {
        connectionService.delete(tokenId = token)

        return responses.ok()
    }
}

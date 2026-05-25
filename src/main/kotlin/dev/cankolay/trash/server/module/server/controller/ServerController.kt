package dev.cankolay.trash.server.module.server.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.web.ApiResponseFactory
import dev.cankolay.trash.server.module.server.dto.response.GetVersionResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/server")
class ServerController(
    private val responses: ApiResponseFactory,
) {
    @GetMapping("/version")
    fun getVersion(): ResponseEntity<ApiResponse<GetVersionResponseDto>> =
        responses.ok(data = GetVersionResponseDto(version = "v0.1.0"))
}

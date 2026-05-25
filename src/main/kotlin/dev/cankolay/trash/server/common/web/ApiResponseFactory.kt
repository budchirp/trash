package dev.cankolay.trash.server.common.web

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.service.I18nService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ApiResponseFactory(private val i18nService: I18nService) {
    fun <T> ok(data: T): ResponseEntity<ApiResponse<T>> =
        ResponseEntity.ok(success(data = data))

    fun ok(): ResponseEntity<ApiResponse<Nothing>> =
        ResponseEntity.ok(success())

    fun <T> created(data: T): ResponseEntity<ApiResponse<T>> =
        ResponseEntity.status(HttpStatus.CREATED).body(success(data = data))

    fun <T> success(data: T): ApiResponse<T> = ApiResponse(
        code = SUCCESS,
        message = i18nService.get(key = SUCCESS),
        data = data
    )

    fun success(): ApiResponse<Nothing> = ApiResponse(
        code = SUCCESS,
        message = i18nService.get(key = SUCCESS)
    )

    fun error(code: String): ApiResponse<Nothing> = ApiResponse(
        error = true,
        code = code,
        message = i18nService.getNullable(key = code) ?: code
    )

    companion object {
        const val SUCCESS = "success"
    }
}

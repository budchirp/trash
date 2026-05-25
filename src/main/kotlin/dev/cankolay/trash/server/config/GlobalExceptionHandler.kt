package dev.cankolay.trash.server.config

import dev.cankolay.trash.server.common.exception.ApiException
import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.service.I18nService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(private val i18nService: I18nService) {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(ApiException::class)
    fun handleApiException(exception: ApiException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity
            .status(exception.status)
            .body(
                ApiResponse(
                    error = true,
                    message = i18nService.getNullable(key = exception.code) ?: exception.message,
                    code = exception.code
                )
            )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(exception: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Map<String, String?>>> {
        val errors = exception.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse(
                    error = true,
                    message = i18nService.get(key = "validation_failed"),
                    code = "validation_failed",
                    data = errors
                )
            )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMalformedJson(exception: HttpMessageNotReadableException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                ApiResponse(
                    error = true,
                    message = i18nService.get(key = "invalid_request"),
                    code = "invalid_request"
                )
            )
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(exception: Exception): ResponseEntity<ApiResponse<Nothing>> {
        logger.error("Unhandled exception", exception)

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse(
                    error = true,
                    message = i18nService.get(key = "internal_server_error"),
                    code = "internal_server_error"
                )
            )
    }
}

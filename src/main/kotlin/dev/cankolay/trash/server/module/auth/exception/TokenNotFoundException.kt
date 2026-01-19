package dev.cankolay.trash.server.module.auth.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class TokenNotFoundException :
    ApiException(status = HttpStatus.NOT_FOUND, code = "token_not_found", message = "Token not found")
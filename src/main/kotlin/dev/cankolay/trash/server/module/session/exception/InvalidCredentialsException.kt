package dev.cankolay.trash.server.module.session.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class InvalidCredentialsException :
    ApiException(status = HttpStatus.UNAUTHORIZED, code = "invalid_credentials", message = "Invalid credentials")

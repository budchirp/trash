package dev.cankolay.trash.server.module.security.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class InvalidSecurityTokenException :
    ApiException(status = HttpStatus.UNAUTHORIZED, code = "invalid_security_token", message = "Invalid security token")

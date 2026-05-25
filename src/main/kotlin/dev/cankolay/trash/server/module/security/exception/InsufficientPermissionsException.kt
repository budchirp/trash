package dev.cankolay.trash.server.module.security.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class InsufficientPermissionsException :
    ApiException(status = HttpStatus.FORBIDDEN, code = "insufficient_permissions", message = "Insufficient permissions")

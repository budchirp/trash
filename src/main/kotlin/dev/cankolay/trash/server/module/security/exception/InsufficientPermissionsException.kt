package dev.cankolay.trash.server.module.security.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class InsufficientPermissionsException :
    ApiException(status = HttpStatus.NOT_FOUND, code = "insufficent_permissions", message = "Insufficient permissions")
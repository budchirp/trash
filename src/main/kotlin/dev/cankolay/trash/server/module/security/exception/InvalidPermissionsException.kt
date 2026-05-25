package dev.cankolay.trash.server.module.security.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class InvalidPermissionsException :
    ApiException(status = HttpStatus.BAD_REQUEST, code = "invalid_permissions", message = "Invalid permissions")

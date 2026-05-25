package dev.cankolay.trash.server.module.user.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class UserNotFoundException :
    ApiException(status = HttpStatus.NOT_FOUND, code = "user_not_found", message = "User not found")

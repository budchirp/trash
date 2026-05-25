package dev.cankolay.trash.server.module.user.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class InvalidProfileGenderException :
    ApiException(
        status = HttpStatus.BAD_REQUEST,
        code = "invalid_profile_gender",
        message = "Gender must be male or female"
    )

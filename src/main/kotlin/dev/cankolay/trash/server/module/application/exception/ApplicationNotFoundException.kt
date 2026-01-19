package dev.cankolay.trash.server.module.application.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class ApplicationNotFoundException :
    ApiException(status = HttpStatus.UNAUTHORIZED, code = "application_not_found", message = "Application not found")
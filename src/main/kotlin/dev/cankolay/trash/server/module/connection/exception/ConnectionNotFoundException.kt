package dev.cankolay.trash.server.module.connection.exception

import dev.cankolay.trash.server.common.exception.ApiException
import org.springframework.http.HttpStatus

class ConnectionNotFoundException :
    ApiException(status = HttpStatus.NOT_FOUND, code = "connection_not_found", message = "Connection not found")
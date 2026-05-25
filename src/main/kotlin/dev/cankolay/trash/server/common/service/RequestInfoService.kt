package dev.cankolay.trash.server.common.service

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service


@Service
class RequestInfoService {
    fun getUserAgent(request: HttpServletRequest) =
        request.getHeader("User-Agent") ?: "Unknown"

    fun getClientIp(request: HttpServletRequest): String = request.remoteAddr ?: "0.0.0.0"
}

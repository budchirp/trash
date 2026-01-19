package dev.cankolay.trash.server.module.security.interceptor

import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.security.annotation.NeedsPermission
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class PermissionInterceptor(
    private val authContext: AuthContext
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        if (handler !is HandlerMethod) return true

        val annotation =
            handler.getMethodAnnotation(NeedsPermission::class.java)
                ?: handler.beanType.getAnnotation(NeedsPermission::class.java)
                ?: return true

        val allowed = annotation.permissions.any { authContext.hasPermission(it) }

        if (!allowed) {
            response.sendError(403, "Insufficient permissions")
            return false
        }

        return true
    }
}

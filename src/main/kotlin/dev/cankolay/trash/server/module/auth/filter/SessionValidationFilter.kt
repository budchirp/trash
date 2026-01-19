package dev.cankolay.trash.server.module.auth.filter

import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.session.service.SessionService
import dev.cankolay.trash.server.module.user.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(2)
class SessionValidationFilter(
    private val sessionService: SessionService,
    private val userService: UserService,
    private val authContext: AuthContext
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (authContext.authenticated) {
            try {
                authContext.user = userService.get(userId = authContext.userId!!)
            } catch (_: Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found")
                return
            }

            try {
                val session = sessionService.get(tokenId = authContext.tokenId!!)

                authContext.permissions = session.token.permissions.toSet()
            } catch (_: Exception) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid session")
                return
            }
        }

        filterChain.doFilter(request, response)
    }
}
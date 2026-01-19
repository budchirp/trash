package dev.cankolay.trash.server.module.auth.filter

import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.module.auth.annotation.Authenticate
import dev.cankolay.trash.server.module.auth.context.AuthContext
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.context.support.WebApplicationContextUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Component
@Order(1)
class AuthenticateFilter(
    private val jwtService: JwtService,
    private val authContext: AuthContext
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = jwtService.extract(token = request.getHeader("Authorization"))
        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing JWT")
            return
        }

        if (!jwtService.verify(jwt = token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT")
            return
        }

        val payload = jwtService.payload(token)

        authContext.authenticated = true

        authContext.userId = payload.id
        authContext.tokenId = payload.token

        filterChain.doFilter(request, response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val handler = getHandler(request) ?: return true
        return !(handler.method.isAnnotationPresent(Authenticate::class.java)
                || handler.beanType.isAnnotationPresent(Authenticate::class.java))
    }

    private fun getHandler(request: HttpServletRequest): HandlerMethod? {
        if (RequestContextHolder.getRequestAttributes() !is ServletRequestAttributes) return null

        val context = WebApplicationContextUtils
            .getRequiredWebApplicationContext(request.servletContext)

        val handlerMapping = context.getBean("requestMappingHandlerMapping") as RequestMappingHandlerMapping
        return handlerMapping.getHandler(request)?.handler as? HandlerMethod
    }
}

package dev.cankolay.trash.server.module.auth.filter

import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.module.auth.service.AuthService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val authService: AuthService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = jwtService.extract(jwt = request.getHeader("Authorization"))
        val principal = jwt?.let { authService.authenticate(jwt = it) }

        if (principal != null) {
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.permissions.map(::SimpleGrantedAuthority)
            )
        } else {
            SecurityContextHolder.clearContext()
        }

        filterChain.doFilter(request, response)
    }
}

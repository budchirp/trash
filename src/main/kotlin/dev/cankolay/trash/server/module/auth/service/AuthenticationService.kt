package dev.cankolay.trash.server.module.auth.service

import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.module.application.entity.Connection
import dev.cankolay.trash.server.module.application.exception.ApplicationNotFoundException
import dev.cankolay.trash.server.module.application.repository.ApplicationRepository
import dev.cankolay.trash.server.module.application.repository.ConnectionRepository
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.session.entity.Token
import dev.cankolay.trash.server.module.session.repository.TokenRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthenticationService(
    private val applicationRepository: ApplicationRepository,
    private val tokenRepository: TokenRepository,
    private val connectionRepository: ConnectionRepository,
    private val authContext: AuthContext,
    private val jwtService: JwtService
) {
    @Transactional
    fun authenticate(applicationId: String, callback: String, permissions: Set<String>): String {
        val application = applicationRepository.findById(applicationId).orElseThrow { ApplicationNotFoundException() }
        val user = authContext.user!!

        val token = tokenRepository.save(
            Token(
                permissions = permissions.toMutableSet()
            )
        )

        connectionRepository.save(
            Connection(
                application = application,
                user = user,
                token = token
            )
        )

        val jwt = jwtService.generate(id = token.id, duration = 1000 * 60 * 60 * 24 * 30)
        
        return "$callback${if (callback.contains("?")) "&" else "?"}token=$jwt"
    }
}

package dev.cankolay.trash.server.module.security.service

import dev.cankolay.trash.server.common.model.JwtPurpose
import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.common.util.Encryptor
import dev.cankolay.trash.server.module.auth.service.AuthService
import dev.cankolay.trash.server.module.security.exception.InvalidSecurityTokenException
import dev.cankolay.trash.server.module.session.exception.InvalidPasswordException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SecurityTokenService(
    private val jwtService: JwtService,
    private val encryptor: Encryptor,
    private val auth: AuthService
) {
    @Transactional(readOnly = true)
    fun create(password: String): String {
        val user = auth.user()

        if (!encryptor.check(password = password, encrypted = user.password)) {
            throw InvalidPasswordException()
        }

        return jwtService.generateSecurityVerificationToken(userId = user.id)
    }

    fun verify(jwt: String) {
        val payload = jwtService.parse(jwt) ?: throw InvalidSecurityTokenException()
        if (payload.purpose != JwtPurpose.SECURITY_VERIFICATION || payload.user != auth.id()) {
            throw InvalidSecurityTokenException()
        }
    }
}

package dev.cankolay.trash.server.module.security.service

import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.common.util.Encryptor
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.session.exception.InvalidPasswordException
import org.springframework.stereotype.Service

@Service
class SecurityTokenService(
    private val jwtService: JwtService,
    private val encryptor: Encryptor,
    private val authContext: AuthContext
) {
    @Throws(InvalidPasswordException::class)
    fun create(password: String): String {
        val user = authContext.user!!

        if (!encryptor.check(password = password, encrypted = user.password)) {
            throw InvalidPasswordException()
        }

        return jwtService.generate(id = user.id, duration = 1000 * 60 * 15)
    }

    fun verify(jwt: String): Boolean {
        if (!jwtService.verify(jwt = jwt)) return false

        if (jwtService.id(jwt = jwt) != authContext.userId) return false

        return true
    }
}
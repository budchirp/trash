package dev.cankolay.trash.server.module.auth.service

import dev.cankolay.trash.server.module.auth.entity.Token
import dev.cankolay.trash.server.module.auth.entity.TokenType
import dev.cankolay.trash.server.module.auth.exception.TokenNotFoundException
import dev.cankolay.trash.server.module.auth.repository.TokenRepository
import dev.cankolay.trash.server.module.security.PermissionKeys
import dev.cankolay.trash.server.module.security.exception.InvalidPermissionsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TokenService(
    private val tokenRepository: TokenRepository
) {
    @Transactional(readOnly = true)
    fun get(id: String): Token =
        tokenRepository.findById(id).orElseThrow { TokenNotFoundException() }

    @Transactional
    fun create(
        type: TokenType = TokenType.SESSION,
        permissionKeys: Set<String> = setOf(PermissionKeys.WILDCARD)
    ): Token {
        if (permissionKeys.isEmpty() || !PermissionKeys.ALL.containsAll(permissionKeys)) {
            throw InvalidPermissionsException()
        }

        return tokenRepository.save(
            Token(
                permissions = permissionKeys.toMutableSet(),
                type = type
            )
        )
    }
}

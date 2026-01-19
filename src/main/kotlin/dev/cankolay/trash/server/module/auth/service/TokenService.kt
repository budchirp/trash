package dev.cankolay.trash.server.module.auth.service

import dev.cankolay.trash.server.module.auth.entity.Token
import dev.cankolay.trash.server.module.auth.exception.TokenNotFoundException
import dev.cankolay.trash.server.module.auth.repository.TokenRepository
import dev.cankolay.trash.server.module.security.repository.PermissionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val permissionRepository: PermissionRepository,
    private val tokenRepository: TokenRepository
) {
    fun get(id: String): Token =
        tokenRepository.findById(id).orElseThrow { TokenNotFoundException() }

    @Transactional
    fun create(permissionKeys: List<String> = listOf("all")): Token {
        return tokenRepository.save(
            Token(
                permissions = permissionRepository.findByKeyIn(keys = permissionKeys.toSet()).toMutableSet()
            )
        )
    }

    @Transactional
    fun delete(id: String) = tokenRepository.deleteById(id)
}
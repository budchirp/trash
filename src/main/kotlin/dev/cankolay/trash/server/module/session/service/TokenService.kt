package dev.cankolay.trash.server.module.session.service

import dev.cankolay.trash.server.module.session.entity.Token
import dev.cankolay.trash.server.module.session.repository.TokenRepository
import org.springframework.stereotype.Service

@Service
class TokenService(
    private val tokenRepository: TokenRepository
) {
    fun create(): Token = tokenRepository.save(Token())
}
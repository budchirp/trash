package dev.cankolay.trash.server.module.session.service

import dev.cankolay.trash.server.common.model.UserAgent
import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.common.util.Encryptor
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.auth.service.TokenService
import dev.cankolay.trash.server.module.session.entity.Session
import dev.cankolay.trash.server.module.session.exception.InvalidPasswordException
import dev.cankolay.trash.server.module.session.exception.SessionNotFoundException
import dev.cankolay.trash.server.module.session.exception.UserNotFoundException
import dev.cankolay.trash.server.module.session.repository.SessionRepository
import dev.cankolay.trash.server.module.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SessionService(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val tokenService: TokenService,
    private val jwtService: JwtService,
    private val authContext: AuthContext,
    private val encryptor: Encryptor
) {
    @Transactional
    fun create(userAgent: UserAgent, ip: String, email: String, password: String): String {
        val user = userRepository.findByEmail(email) ?: throw UserNotFoundException()

        if (!encryptor.check(password = password, encrypted = user.password)) {
            throw InvalidPasswordException()
        }

        val token = tokenService.create()

        val session = sessionRepository.save(
            Session(
                token = token,

                ip = ip,

                device = userAgent.device,
                platform = userAgent.platform,

                os = userAgent.os,
                browser = userAgent.browser,

                user = user
            )
        )

        return jwtService.generate(userId = user.id, token = session.token)
    }

    fun exists(tokenId: String, userId: String) =
        sessionRepository.existsByTokenIdAndUserId(tokenId = tokenId, userId = userId)

    @Transactional
    fun getAll(): List<Session> = sessionRepository.findAllByUserId(userId = authContext.userId!!)

    @Transactional
    fun get(tokenId: String): Session =
        sessionRepository.findByTokenIdAndUserId(tokenId = tokenId, userId = authContext.userId!!)
            ?: throw SessionNotFoundException()

    @Transactional
    fun get(): Session = get(tokenId = authContext.tokenId!!)

    @Transactional
    fun delete(tokenId: String) {
        if (!exists(tokenId = tokenId, userId = authContext.userId!!)) {
            throw SessionNotFoundException()
        }

        sessionRepository.deleteByTokenId(tokenId = tokenId)

        tokenService.delete(id = tokenId)
    }

    @Transactional
    fun delete() = delete(tokenId = authContext.tokenId!!)
}
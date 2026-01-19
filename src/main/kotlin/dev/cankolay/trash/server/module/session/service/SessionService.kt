package dev.cankolay.trash.server.module.session.service

import dev.cankolay.trash.server.common.model.UserAgent
import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.common.util.Encryptor
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.session.entity.Session
import dev.cankolay.trash.server.module.session.exception.InvalidPasswordException
import dev.cankolay.trash.server.module.session.exception.SessionNotFoundException
import dev.cankolay.trash.server.module.session.exception.UnauthorizedException
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
    @Throws(InvalidPasswordException::class, UserNotFoundException::class)
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

        return jwtService.generate(id = user.id, token = session.token)
    }

    fun exists(tokenId: String, userId: String) =
        sessionRepository.existsByTokenIdAndUserId(token = tokenId, user = userId)

    fun getAll(): List<Session> = sessionRepository.findAllByUserId(user = authContext.userId!!)

    @Throws(SessionNotFoundException::class)
    fun get(tokenId: String): Session =
        sessionRepository.findByTokenIdAndUserId(token = tokenId, user = authContext.userId!!)
            ?: throw SessionNotFoundException()

    fun get(): Session = get(tokenId = authContext.tokenId!!)

    @Transactional
    @Throws(UnauthorizedException::class)
    fun delete(tokenId: String) {
        if (!exists(tokenId = tokenId, userId = authContext.userId!!)) {
            throw SessionNotFoundException()
        }

        sessionRepository.deleteByTokenId(token = tokenId)
    }

    @Transactional
    fun delete() = delete(tokenId = authContext.tokenId!!)
}
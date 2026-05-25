package dev.cankolay.trash.server.module.user.service

import dev.cankolay.trash.server.common.util.Encryptor
import dev.cankolay.trash.server.module.application.repository.ApplicationRepository
import dev.cankolay.trash.server.module.auth.service.AuthService
import dev.cankolay.trash.server.module.connection.repository.ConnectionRepository
import dev.cankolay.trash.server.module.security.service.SecurityTokenService
import dev.cankolay.trash.server.module.session.repository.SessionRepository
import dev.cankolay.trash.server.module.user.entity.Profile
import dev.cankolay.trash.server.module.user.entity.User
import dev.cankolay.trash.server.module.user.exception.UserExistsException
import dev.cankolay.trash.server.module.user.exception.UserNotFoundException
import dev.cankolay.trash.server.module.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val connectionRepository: ConnectionRepository,
    private val applicationRepository: ApplicationRepository,
    private val auth: AuthService,
    private val encryptor: Encryptor,
    private val securityTokenService: SecurityTokenService
) {
    @Transactional
    fun create(email: String, username: String, password: String): User {
        if (userRepository.existsByEmailOrUsername(email = email, username = username)) {
            throw UserExistsException()
        }

        return userRepository.save(
            User(
                email = email,
                username = username,
                password = encryptor.encrypt(password = password),
                profile = Profile()
            )
        )
    }

    @Transactional(readOnly = true)
    fun get(id: String): User = userRepository.findById(id).orElseThrow { UserNotFoundException() }

    @Transactional(readOnly = true)
    fun get(): User = auth.user()

    @Transactional
    fun delete(securityToken: String) {
        securityTokenService.verify(jwt = securityToken)

        val user = auth.user()
        sessionRepository.deleteAll(sessionRepository.findAllByUserId(user.id))
        connectionRepository.deleteAll(connectionRepository.findAllByUserId(user.id))
        connectionRepository.deleteAll(connectionRepository.findAllByApplicationOwnerId(ownerId = user.id))

        sessionRepository.flush()
        connectionRepository.flush()

        applicationRepository.deleteAllByOwnerId(ownerId = user.id)

        userRepository.delete(user)
    }
}

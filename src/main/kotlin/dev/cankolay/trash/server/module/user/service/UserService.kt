package dev.cankolay.trash.server.module.user.service

import dev.cankolay.trash.server.common.util.Encryptor
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.security.service.SecurityTokenService
import dev.cankolay.trash.server.module.session.exception.UnauthorizedException
import dev.cankolay.trash.server.module.session.exception.UserNotFoundException
import dev.cankolay.trash.server.module.session.repository.SessionRepository
import dev.cankolay.trash.server.module.user.entity.User
import dev.cankolay.trash.server.module.user.exception.InvalidVerificationTokenException
import dev.cankolay.trash.server.module.user.exception.UserExistsException
import dev.cankolay.trash.server.module.user.repository.ProfileRepository
import dev.cankolay.trash.server.module.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val profileService: ProfileService,
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
    private val sessionRepository: SessionRepository,
    private val authContext: AuthContext,
    private val encryptor: Encryptor,
    private val securityTokenService: SecurityTokenService
) {
    @Transactional
    @Throws(UserExistsException::class)
    fun create(email: String, username: String, password: String): User {
        if (exists(email = email, username = username)) {
            throw UserExistsException()
        }

        val profile = profileService.create()

        return userRepository.save(
            User(
                email = email,
                username = username,
                password = encryptor.encrypt(password),
                profile = profile
            )
        )
    }

    fun exists(email: String, username: String) =
        userRepository.existsByEmailAndUsername(email = email, username = username)

    @Throws(UserNotFoundException::class)
    fun get(userId: String): User {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException() }
        return user.copy()
    }

    fun get(): User = get(userId = authContext.userId!!)

    @Transactional
    @Throws(InvalidVerificationTokenException::class, UnauthorizedException::class)
    fun delete(securityTokenJWT: String) {
        if (!securityTokenService.verify(jwt = securityTokenJWT)) {
            throw InvalidVerificationTokenException()
        }

        val user = authContext.user!!

        sessionRepository.deleteAllByUserId(user = user.id)
        profileRepository.deleteById(user.profile.id)

        userRepository.deleteById(user.id)
    }
}
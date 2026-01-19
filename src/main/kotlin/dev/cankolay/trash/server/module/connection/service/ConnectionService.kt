package dev.cankolay.trash.server.module.connection.service

import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.module.application.exception.ApplicationNotFoundException
import dev.cankolay.trash.server.module.application.repository.ApplicationRepository
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.auth.entity.Token
import dev.cankolay.trash.server.module.auth.repository.TokenRepository
import dev.cankolay.trash.server.module.auth.service.TokenService
import dev.cankolay.trash.server.module.connection.entity.Connection
import dev.cankolay.trash.server.module.connection.exception.ConnectionNotFoundException
import dev.cankolay.trash.server.module.connection.repository.ConnectionRepository
import dev.cankolay.trash.server.module.security.repository.PermissionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConnectionService(
    private val applicationRepository: ApplicationRepository,
    private val tokenRepository: TokenRepository,
    private val connectionRepository: ConnectionRepository,
    private val authContext: AuthContext,
    private val jwtService: JwtService,
    private val permissionRepository: PermissionRepository,
    private val tokenService: TokenService
) {
    @Transactional
    fun connect(applicationId: String, callback: String, permissions: Set<String>): String {
        val application = applicationRepository.findById(applicationId).orElseThrow { ApplicationNotFoundException() }
        val user = authContext.user!!

        val token = tokenRepository.save(
            Token(
                permissions = permissionRepository.findByKeyIn(keys = permissions.toSet()).toMutableSet()
            )
        )

        connectionRepository.save(
            Connection(
                application = application,
                user = user,
                token = token
            )
        )

        val jwtToken = jwtService.generate(userId = user.id, token = token)

        return "$callback${if (callback.contains("?")) "&" else "?"}token=$jwtToken"
    }

    @Transactional
    fun getAll(): List<Connection> {
        val user = authContext.user!!
        return connectionRepository.findAllByUserId(user.id)
    }

    @Transactional
    fun get(id: Long): Connection = connectionRepository.findByIdAndUserId(id = id, userId = authContext.userId!!)
        ?: throw ConnectionNotFoundException()

    @Transactional
    fun get(tokenId: String): Connection =
        connectionRepository.findByTokenIdAndUserId(tokenId = tokenId, userId = authContext.userId!!)
            ?: throw ConnectionNotFoundException()

    @Transactional
    fun delete(id: Long) {
        val connection = get(id)

        tokenService.delete(id = connection.token.id)

        connectionRepository.deleteById(id)
    }
}

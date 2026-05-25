package dev.cankolay.trash.server.module.connection.service

import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.module.application.exception.ApplicationNotFoundException
import dev.cankolay.trash.server.module.application.repository.ApplicationRepository
import dev.cankolay.trash.server.module.auth.entity.TokenType
import dev.cankolay.trash.server.module.auth.service.AuthService
import dev.cankolay.trash.server.module.auth.service.TokenService
import dev.cankolay.trash.server.module.connection.entity.Connection
import dev.cankolay.trash.server.module.connection.exception.ConnectionNotFoundException
import dev.cankolay.trash.server.module.connection.repository.ConnectionRepository
import dev.cankolay.trash.server.module.security.PermissionKeys
import dev.cankolay.trash.server.module.security.exception.InsufficientPermissionsException
import dev.cankolay.trash.server.module.security.exception.InvalidPermissionsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConnectionService(
    private val applicationRepository: ApplicationRepository,
    private val connectionRepository: ConnectionRepository,
    private val auth: AuthService,
    private val jwtService: JwtService,
    private val tokenService: TokenService
) {
    @Transactional
    fun connect(applicationId: String, permissions: Set<String>): String {
        if (permissions.isEmpty() || PermissionKeys.WILDCARD in permissions) {
            throw InvalidPermissionsException()
        }

        if (auth.token().type != TokenType.SESSION) {
            throw InsufficientPermissionsException()
        }

        if (!auth.permissions().containsAll(elements = permissions)) {
            throw InsufficientPermissionsException()
        }

        val user = auth.user()
        val application = applicationRepository.findById(applicationId).orElseThrow { ApplicationNotFoundException() }

        connectionRepository.findByApplicationIdAndUserId(applicationId = applicationId, userId = user.id)?.let {
            it.token.grant(permissionKeys = permissions)
            return jwtService.generateAccessToken(userId = user.id, token = it.token)
        }

        val token = tokenService.create(type = TokenType.CONNECTION, permissionKeys = permissions)
        connectionRepository.save(
            Connection(
                application = application,
                user = user,
                token = token
            )
        )

        return jwtService.generateAccessToken(userId = user.id, token = token)
    }

    @Transactional(readOnly = true)
    fun getAll(): List<Connection> = connectionRepository.findAllByUserId(userId = auth.id())

    @Transactional(readOnly = true)
    fun get(tokenId: String): Connection =
        connectionRepository.findByTokenIdAndUserId(tokenId = tokenId, userId = auth.id())
            ?: throw ConnectionNotFoundException()

    @Transactional
    fun delete(tokenId: String) {
        connectionRepository.delete(get(tokenId = tokenId))
    }
}

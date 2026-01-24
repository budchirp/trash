package dev.cankolay.trash.server.module.connection.service

import dev.cankolay.trash.server.common.service.JwtService
import dev.cankolay.trash.server.module.application.exception.ApplicationNotFoundException
import dev.cankolay.trash.server.module.application.repository.ApplicationRepository
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.auth.entity.TokenType
import dev.cankolay.trash.server.module.auth.service.TokenService
import dev.cankolay.trash.server.module.connection.entity.Connection
import dev.cankolay.trash.server.module.connection.exception.ConnectionNotFoundException
import dev.cankolay.trash.server.module.connection.repository.ConnectionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ConnectionService(
    private val applicationRepository: ApplicationRepository,
    private val connectionRepository: ConnectionRepository,
    private val authContext: AuthContext,
    private val jwtService: JwtService,
    private val tokenService: TokenService
) {
    @Transactional
    fun connect(applicationId: String, callback: String, permissions: List<String>): String {
        val application = applicationRepository.findById(applicationId).orElseThrow { ApplicationNotFoundException() }
        val user = authContext.user!!

        val token = tokenService.create(type = TokenType.CONNECTION, permissionKeys = permissions)

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
    fun get(tokenId: String): Connection =
        connectionRepository.findByTokenIdAndUserId(tokenId = tokenId, userId = authContext.userId!!)
            ?: throw ConnectionNotFoundException()

    @Transactional
    fun delete(tokenId: String) {
        val connection = get(tokenId = tokenId)

        connectionRepository.deleteByTokenIdAndUserId(tokenId = tokenId, userId = authContext.userId!!)

        tokenService.delete(id = connection.token.id)
    }
}

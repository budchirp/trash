package dev.cankolay.trash.server.common.util

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.auth.entity.TokenType
import dev.cankolay.trash.server.module.auth.service.TokenService
import dev.cankolay.trash.server.module.connection.service.ConnectionService
import dev.cankolay.trash.server.module.security.exception.InsufficientPermissionsException
import dev.cankolay.trash.server.module.security.repository.PermissionRepository
import dev.cankolay.trash.server.module.session.service.SessionService
import dev.cankolay.trash.server.module.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class Controller(
    private val authContext: AuthContext,
    private val userService: UserService,
    private val sessionService: SessionService,
    private val tokenService: TokenService,
    private val connectionService: ConnectionService,
    private val permissionRepository: PermissionRepository
) {
    operator fun <T> invoke(
        permissions: List<String> = emptyList(),
        block: () -> ResponseEntity<ApiResponse<T>>,
    ): ResponseEntity<ApiResponse<T>> {
        if (authContext.authenticated) {
            authContext.user = userService.get(id = authContext.userId!!)

            val token = tokenService.get(id = authContext.tokenId!!)
            when (token.type) {
                TokenType.SESSION -> {
                    val session = sessionService.get(tokenId = authContext.tokenId!!)
                    authContext.permissions = session.token.permissions.toSet()
                }

                TokenType.CONNECTION -> {
                    val connection = connectionService.get(tokenId = authContext.tokenId!!)
                    authContext.permissions = connection.token.permissions.toSet()
                }
            }

            if (authContext.permissions.any { it.key == "*" }) {
                authContext.permissions = permissionRepository.findAll().toSet()
            }

            val permissions = permissionRepository.findByKeyIn(keys = permissions.toSet())
            if (!permissions.any { authContext.hasPermission(it) }) {
                throw InsufficientPermissionsException()
            }
        }

        return block()
    }
}
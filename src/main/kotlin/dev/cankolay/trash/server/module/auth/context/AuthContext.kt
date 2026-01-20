package dev.cankolay.trash.server.module.auth.context

import dev.cankolay.trash.server.module.security.entity.Permission
import dev.cankolay.trash.server.module.user.entity.User
import org.springframework.stereotype.Component
import org.springframework.web.context.annotation.RequestScope

@Component
@RequestScope
class AuthContext {
    var authenticated: Boolean = false

    var userId: String? = null
    var tokenId: String? = null

    var user: User? = null

    var permissions: Set<Permission> = emptySet()

    fun hasPermission(permission: Permission): Boolean =
        permissions.contains(element = permission)
}

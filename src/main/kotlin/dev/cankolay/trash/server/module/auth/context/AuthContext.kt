package dev.cankolay.trash.server.module.auth.context

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
        get() = field

    var permissions: Set<String> = emptySet()

    fun hasPermission(permission: String): Boolean =
        permissions.contains(permission)
}

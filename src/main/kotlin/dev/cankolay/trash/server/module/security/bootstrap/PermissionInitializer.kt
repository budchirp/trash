package dev.cankolay.trash.server.module.security.bootstrap

import dev.cankolay.trash.server.module.security.entity.Permission
import dev.cankolay.trash.server.module.security.repository.PermissionRepository
import jakarta.transaction.Transactional
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class PermissionInitializer(
    private val permissionRepository: PermissionRepository
) : ApplicationRunner {
    companion object {
        val PERMISSONS = listOf(
            "user:read",
            "profile:read",
            "profile:write",
            "session:read",
            "session:revoke",
            "all"
        )
    }

    @Transactional
    override fun run(args: ApplicationArguments) {
        val permissions = PERMISSONS.toSet()

        val existing = permissionRepository.findByKeyIn(permissions)
            .map { it.key }
            .toSet()

        val missing = permissions - existing

        if (missing.isNotEmpty()) {
            permissionRepository.saveAll(
                missing.map { Permission(key = it) }
            )
        }
    }
}

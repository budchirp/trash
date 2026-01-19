package dev.cankolay.trash.server.module.security.repository;

import dev.cankolay.trash.server.module.security.entity.Permission
import org.springframework.data.jpa.repository.JpaRepository;

interface PermissionRepository : JpaRepository<Permission, Long> {
    fun findByKeyIn(keys: Collection<String>): List<Permission>
}
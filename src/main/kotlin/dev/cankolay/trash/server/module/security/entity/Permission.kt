package dev.cankolay.trash.server.module.security.entity

import dev.cankolay.trash.server.module.security.dto.PermissionDto
import jakarta.persistence.*

@Entity
@Table(name = "permissions")
data class Permission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val key: String
)

fun Permission.toDto() = PermissionDto(
    key = this.key
)
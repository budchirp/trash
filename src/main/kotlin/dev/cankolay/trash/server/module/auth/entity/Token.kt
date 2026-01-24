package dev.cankolay.trash.server.module.auth.entity

import dev.cankolay.trash.server.module.auth.dto.TokenDto
import dev.cankolay.trash.server.module.security.entity.Permission
import dev.cankolay.trash.server.module.security.entity.toDto
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

enum class TokenType {
    SESSION,
    CONNECTION
}

@Entity
@Table(name = "tokens")
data class Token(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Enumerated(value = EnumType.STRING)
    val type: TokenType = TokenType.SESSION,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "token_permissions",
        joinColumns = [JoinColumn(name = "token_id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id")]
    )
    val permissions: MutableSet<Permission> = mutableSetOf(),

    @Column(name = "expires_at", nullable = false)
    val expiresAt: Instant = Instant.now().plusSeconds(30L * 24 * 60 * 60),

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant? = null,
)


fun Token.toDto() = TokenDto(
    id = this.id,
    expiresAt = this.expiresAt.toString(),
    permissions = this.permissions.map { it.toDto() }
)
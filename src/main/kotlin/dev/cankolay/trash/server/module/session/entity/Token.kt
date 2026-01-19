package dev.cankolay.trash.server.module.session.entity

import dev.cankolay.trash.server.module.session.dto.TokenDto
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "tokens")
data class Token(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "token_permissions",
        joinColumns = [JoinColumn(name = "token_id")]
    )
    @Column(name = "permission", nullable = false)
    val permissions: MutableSet<String> = mutableSetOf(),

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
    token = this.id,
    expiresAt = this.expiresAt.toString()
)
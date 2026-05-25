package dev.cankolay.trash.server.module.auth.entity

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
class Token(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val type: TokenType = TokenType.SESSION,

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

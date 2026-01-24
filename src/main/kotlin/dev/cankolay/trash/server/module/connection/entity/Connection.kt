package dev.cankolay.trash.server.module.connection.entity

import dev.cankolay.trash.server.module.application.entity.Application
import dev.cankolay.trash.server.module.application.entity.toDto
import dev.cankolay.trash.server.module.auth.entity.Token
import dev.cankolay.trash.server.module.auth.entity.toDto
import dev.cankolay.trash.server.module.connection.dto.ConnectionDto
import dev.cankolay.trash.server.module.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "connections")
data class Connection(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id", nullable = false)
    val token: Token,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    val application: Application,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant? = null,
)

fun Connection.toDto() = ConnectionDto(
    application = this.application.toDto(),
    token = this.token.toDto(),
    createdAt = this.createdAt.toString()
)
package dev.cankolay.trash.server.module.connection.entity

import dev.cankolay.trash.server.module.application.entity.Application
import dev.cankolay.trash.server.module.auth.entity.Token
import dev.cankolay.trash.server.module.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(
    name = "connections",
    uniqueConstraints = [UniqueConstraint(
        name = "uk_connections_application_user",
        columnNames = ["application_id", "user_id"]
    )]
)
class Connection(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "token_id", nullable = false, unique = true)
    val token: Token,

    @ManyToOne(fetch = FetchType.EAGER)
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

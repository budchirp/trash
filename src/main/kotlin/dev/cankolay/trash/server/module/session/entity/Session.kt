package dev.cankolay.trash.server.module.session.entity

import dev.cankolay.trash.server.common.model.UserAgentPlatform
import dev.cankolay.trash.server.module.auth.entity.Token
import dev.cankolay.trash.server.module.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "sessions")
class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "token_id", nullable = false, unique = true)
    val token: Token,

    @Column(nullable = false)
    val ip: String,

    @Column(nullable = false)
    val device: String,

    @Column(nullable = false)
    val os: String,

    @Column(nullable = false)
    val browser: String,

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    val platform: UserAgentPlatform,

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

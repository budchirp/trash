package dev.cankolay.trash.server.module.session.entity

import dev.cankolay.trash.server.common.model.UserAgentPlatform
import dev.cankolay.trash.server.module.auth.entity.Token
import dev.cankolay.trash.server.module.auth.entity.toDto
import dev.cankolay.trash.server.module.session.dto.SessionDto
import dev.cankolay.trash.server.module.session.dto.SessionDtoDevice
import dev.cankolay.trash.server.module.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@Entity
@Table(name = "sessions")
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "token_id", nullable = false)
    val token: Token,

    val ip: String,
    val device: String,

    val os: String,
    val browser: String,

    @Enumerated(value = EnumType.STRING)
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

fun Session.toDto() = SessionDto(
    token = this.token.toDto(),
    ip = this.ip,
    browser = this.browser,
    device = SessionDtoDevice(
        name = this.device,
        platform = this.platform.name,
        os = this.os
    ),
    created_at = this.createdAt.toString()
)
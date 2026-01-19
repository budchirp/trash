package dev.cankolay.trash.server.module.user.entity

import dev.cankolay.trash.server.module.session.entity.Session
import dev.cankolay.trash.server.module.user.dto.UserDto
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
data class User(
    @Id
    val id: String = UUID.randomUUID().toString(),

    val email: String,
    var username: String,

    val password: String,

    val verified: Boolean = false,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    var profile: Profile,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    var sessions: MutableSet<Session> = mutableSetOf(),

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant? = null,
)

fun User.toDto() = UserDto(
    id = this.id,
    email = this.email,
    username = this.username,
    profile = this.profile.toDto()
)

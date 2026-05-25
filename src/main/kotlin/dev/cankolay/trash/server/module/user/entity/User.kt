package dev.cankolay.trash.server.module.user.entity

import dev.cankolay.trash.server.module.session.entity.Session
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "users")
class User(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false, unique = true)
    var username: String,

    @Column(nullable = false)
    val password: String,

    @OneToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    var profile: Profile = Profile(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE], orphanRemoval = true)
    var sessions: MutableSet<Session> = mutableSetOf(),

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant? = null,
)

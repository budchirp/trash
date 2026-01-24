package dev.cankolay.trash.server.module.application.entity

import dev.cankolay.trash.server.module.application.dto.ApplicationDto
import dev.cankolay.trash.server.module.user.entity.User
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.util.*

@Entity
@Table(name = "applications")
data class Application(
    @Id
    val id: String = UUID.randomUUID().toString(),

    val name: String,
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    val createdAt: Instant? = null,

    @UpdateTimestamp
    @Column(name = "updated_at")
    val updatedAt: Instant? = null,
)

fun Application.toDto() = ApplicationDto(
    id = this.id,
    name = this.name,
    description = this.description,
    createdAt = this.createdAt.toString()
)

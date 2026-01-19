package dev.cankolay.trash.server.module.user.entity

import dev.cankolay.trash.server.module.user.dto.ProfileDto
import jakarta.persistence.*

@Entity
@Table(name = "profiles")
data class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    var name: String? = null,
    var picture: String? = null,
)

fun Profile.toDto() = ProfileDto(
    name = this.name,
    picture = this.picture
)

package dev.cankolay.trash.server.module.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "profiles")
class Profile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(length = 100)
    var name: String? = null,

    @Column(length = 2048)
    var picture: String? = null,

    @Enumerated(EnumType.STRING)
    var gender: ProfileGender? = null,
)

enum class ProfileGender(val value: String) {
    MALE("male"),
    FEMALE("female");

    companion object {
        fun fromValue(value: String): ProfileGender? =
            entries.firstOrNull { it.value.equals(value, ignoreCase = true) }
    }
}

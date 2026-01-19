package dev.cankolay.trash.server.module.user.repository

import dev.cankolay.trash.server.module.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String> {
    fun findByEmail(email: String): User?

    fun existsByEmailAndUsername(email: String, username: String): Boolean
}
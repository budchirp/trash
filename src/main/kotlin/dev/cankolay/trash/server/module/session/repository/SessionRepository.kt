package dev.cankolay.trash.server.module.session.repository

import dev.cankolay.trash.server.module.session.entity.Session
import org.springframework.data.jpa.repository.JpaRepository

interface SessionRepository : JpaRepository<Session, Long> {
    fun findByTokenIdAndUserId(tokenId: String, userId: String): Session?

    fun findAllByUserId(userId: String): List<Session>
}

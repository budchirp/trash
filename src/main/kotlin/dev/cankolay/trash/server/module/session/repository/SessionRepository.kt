package dev.cankolay.trash.server.module.session.repository

import dev.cankolay.trash.server.module.session.entity.Session
import org.springframework.data.jpa.repository.JpaRepository

interface SessionRepository : JpaRepository<Session, String> {
    fun findByTokenIdAndUserId(token: String, user: String): Session?
    fun existsByTokenIdAndUserId(token: String, user: String): Boolean
    fun deleteByTokenId(token: String)

    fun deleteAllByUserId(user: String): Long
    fun findAllByUserId(user: String): List<Session>
}
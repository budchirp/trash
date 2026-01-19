package dev.cankolay.trash.server.module.session.repository

import dev.cankolay.trash.server.module.session.entity.Session
import org.springframework.data.jpa.repository.JpaRepository

interface SessionRepository : JpaRepository<Session, String> {
    fun findByTokenIdAndUserId(tokenId: String, userId: String): Session?
    fun existsByTokenIdAndUserId(tokenId: String, userId: String): Boolean
    fun deleteByTokenId(tokenId: String)

    fun deleteAllByUserId(userId: String): Long
    fun findAllByUserId(userId: String): List<Session>
}
package dev.cankolay.trash.server.module.connection.repository

import dev.cankolay.trash.server.module.connection.entity.Connection
import org.springframework.data.jpa.repository.JpaRepository

interface ConnectionRepository : JpaRepository<Connection, Long> {
    fun findByTokenIdAndUserId(tokenId: String, userId: String): Connection?
    fun deleteByTokenIdAndUserId(tokenId: String, userId: String)

    fun findAllByUserId(userId: String): List<Connection>
}
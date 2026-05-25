package dev.cankolay.trash.server.module.connection.repository

import dev.cankolay.trash.server.module.connection.entity.Connection
import org.springframework.data.jpa.repository.JpaRepository

interface ConnectionRepository : JpaRepository<Connection, Long> {
    fun findByApplicationIdAndUserId(applicationId: String, userId: String): Connection?
    fun findAllByApplicationIdAndUserId(applicationId: String, userId: String): List<Connection>
    fun findByTokenIdAndUserId(tokenId: String, userId: String): Connection?

    fun findAllByUserId(userId: String): List<Connection>
}

package dev.cankolay.trash.server.module.connection.repository

import dev.cankolay.trash.server.module.auth.entity.Token
import dev.cankolay.trash.server.module.connection.entity.Connection
import org.springframework.data.jpa.repository.JpaRepository

interface ConnectionRepository : JpaRepository<Connection, Long> {
    fun findByTokenIdAndUserId(tokenId: String, userId: String): Connection?
    fun findByIdAndUserId(id: Long, userId: String): Connection?

    fun findAllByUserId(userId: String): List<Connection>
    fun token(token: Token): MutableList<Connection>
}
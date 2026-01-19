package dev.cankolay.trash.server.module.application.repository

import dev.cankolay.trash.server.module.application.entity.Connection
import org.springframework.data.jpa.repository.JpaRepository

interface ConnectionRepository : JpaRepository<Connection, Long>

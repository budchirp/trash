package dev.cankolay.trash.server.module.application.repository

import dev.cankolay.trash.server.module.application.entity.Application
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationRepository : JpaRepository<Application, String> {
    fun existsByIdAndUserId(id: String, userId: String): Boolean

    fun findAllByUserId(userId: String): List<Application>
}

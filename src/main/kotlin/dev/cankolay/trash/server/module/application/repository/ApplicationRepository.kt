package dev.cankolay.trash.server.module.application.repository

import dev.cankolay.trash.server.module.application.entity.Application
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationRepository : JpaRepository<Application, String> {
    fun existsByIdAndUserId(id: String, user: String): Boolean

    fun findAllByUserId(user: String): List<Application>
}

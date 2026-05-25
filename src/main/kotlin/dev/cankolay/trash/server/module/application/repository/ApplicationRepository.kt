package dev.cankolay.trash.server.module.application.repository

import dev.cankolay.trash.server.module.application.entity.Application
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationRepository : JpaRepository<Application, String> {
    fun findByIdAndUserId(id: String, userId: String): Application?

    fun findAllByUserId(userId: String): List<Application>

    fun deleteAllByUserId(userId: String): Long
}

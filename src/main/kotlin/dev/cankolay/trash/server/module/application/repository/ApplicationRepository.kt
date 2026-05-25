package dev.cankolay.trash.server.module.application.repository

import dev.cankolay.trash.server.module.application.entity.Application
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicationRepository : JpaRepository<Application, String> {
    fun findByIdAndOwnerId(id: String, ownerId: String): Application?

    fun findAllByOwnerId(ownerId: String): List<Application>

    fun deleteAllByOwnerId(ownerId: String): Long
}

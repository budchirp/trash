package dev.cankolay.trash.server.module.application.service

import dev.cankolay.trash.server.module.application.entity.Application
import dev.cankolay.trash.server.module.application.exception.ApplicationNotFoundException
import dev.cankolay.trash.server.module.application.repository.ApplicationRepository
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.session.exception.UnauthorizedException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository,
    private val authContext: AuthContext
) {
    @Transactional
    fun create(name: String, description: String): Application {
        val user = authContext.user!!

        return applicationRepository.save(
            Application(
                name = name,
                description = description,
                user = user
            )
        )
    }

    fun exists(id: String, userId: String) =
        applicationRepository.existsByIdAndUserId(id = id, userId = userId)

    @Transactional
    fun getAll(): List<Application> {
        return applicationRepository.findAllByUserId(userId = authContext.userId!!)
    }

    @Transactional
    fun get(id: String): Application =
        applicationRepository.findById(id).orElseThrow { ApplicationNotFoundException() }

    @Transactional
    fun delete(id: String) {
        if (!exists(id = id, userId = authContext.userId!!)) {
            throw UnauthorizedException()
        }

        applicationRepository.deleteById(id)
    }
}

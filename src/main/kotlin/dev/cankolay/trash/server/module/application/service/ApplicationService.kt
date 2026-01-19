package dev.cankolay.trash.server.module.application.service

import dev.cankolay.trash.server.module.application.entity.Application
import dev.cankolay.trash.server.module.application.exception.ApplicationNotFoundException
import dev.cankolay.trash.server.module.application.repository.ApplicationRepository
import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.session.exception.UnauthorizedException
import org.springframework.stereotype.Service

@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository,
    private val authContext: AuthContext
) {
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

    fun exists(applicationId: String, userId: String) =
        applicationRepository.existsByIdAndUserId(id = applicationId, user = userId)

    fun getAll(): List<Application> {
        return applicationRepository.findAllByUserId(user = authContext.userId!!)
    }

    fun get(applicationId: String): Application =
        applicationRepository.findById(applicationId).orElseThrow { ApplicationNotFoundException() }

    fun delete(applicationId: String) {
        if (!exists(applicationId = applicationId, userId = authContext.userId!!)) {
            throw UnauthorizedException()
        }

        applicationRepository.deleteById(applicationId)
    }
}

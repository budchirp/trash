package dev.cankolay.trash.server.module.application.service

import dev.cankolay.trash.server.module.application.entity.Application
import dev.cankolay.trash.server.module.application.exception.ApplicationNotFoundException
import dev.cankolay.trash.server.module.application.repository.ApplicationRepository
import dev.cankolay.trash.server.module.auth.service.AuthService
import dev.cankolay.trash.server.module.connection.repository.ConnectionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository,
    private val connectionRepository: ConnectionRepository,
    private val auth: AuthService
) {
    @Transactional
    fun create(name: String, description: String, icon: String): Application {
        return applicationRepository.save(
            Application(
                name = name,
                description = description,
                icon = icon,
                user = auth.user()
            )
        )
    }

    @Transactional(readOnly = true)
    fun getAll(): List<Application> = applicationRepository.findAllByUserId(userId = auth.id())

    @Transactional(readOnly = true)
    fun get(id: String): Application =
        applicationRepository.findByIdAndUserId(id = id, userId = auth.id())
            ?: throw ApplicationNotFoundException()

    @Transactional
    fun delete(id: String) {
        val application = get(id)
        connectionRepository.deleteAll(
            connectionRepository.findAllByApplicationIdAndUserId(
                applicationId = id,
                userId = auth.id()
            )
        )
        applicationRepository.delete(application)
    }
}

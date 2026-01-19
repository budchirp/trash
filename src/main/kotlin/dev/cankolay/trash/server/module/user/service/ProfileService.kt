package dev.cankolay.trash.server.module.user.service

import dev.cankolay.trash.server.module.auth.context.AuthContext
import dev.cankolay.trash.server.module.user.entity.Profile
import dev.cankolay.trash.server.module.user.repository.ProfileRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val authContext: AuthContext,
) {
    @Transactional
    fun create(): Profile = profileRepository.save(Profile())

    @Transactional
    fun update(name: String?) {
        val user = authContext.user!!

        user.profile.name = name ?: user.profile.name

        profileRepository.save(user.profile)
    }
}
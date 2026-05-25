package dev.cankolay.trash.server.module.user.service

import dev.cankolay.trash.server.module.auth.service.AuthService
import dev.cankolay.trash.server.module.user.dto.request.ProfileRequestDto
import dev.cankolay.trash.server.module.user.entity.Profile
import dev.cankolay.trash.server.module.user.entity.ProfileGender
import dev.cankolay.trash.server.module.user.exception.InvalidProfileGenderException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProfileService(
    private val auth: AuthService,
) {
    @Transactional
    fun update(body: ProfileRequestDto): Profile {
        val user = auth.user()

        user.profile.name = body.name ?: user.profile.name
        user.profile.picture = body.picture ?: user.profile.picture
        user.profile.gender = body.gender?.let {
            ProfileGender.fromValue(value = it) ?: throw InvalidProfileGenderException()
        } ?: user.profile.gender

        return user.profile
    }
}

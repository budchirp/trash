package dev.cankolay.trash.server.module.user.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.web.ApiResponseFactory
import dev.cankolay.trash.server.module.user.dto.ProfileDto
import dev.cankolay.trash.server.module.user.dto.request.ProfileRequestDto
import dev.cankolay.trash.server.module.user.mapper.toDto
import dev.cankolay.trash.server.module.user.service.ProfileService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user/profile")
class ProfileController(
    private val responses: ApiResponseFactory,
    private val profileService: ProfileService
) {
    @PatchMapping
    fun update(@Valid @RequestBody body: ProfileRequestDto): ResponseEntity<ApiResponse<ProfileDto>> =
        responses.ok(data = profileService.update(body = body).toDto())
}

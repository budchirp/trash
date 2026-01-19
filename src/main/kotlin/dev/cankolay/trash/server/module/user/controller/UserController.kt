package dev.cankolay.trash.server.module.user.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.service.I18nService
import dev.cankolay.trash.server.common.util.Controller
import dev.cankolay.trash.server.module.auth.annotation.Authenticate
import dev.cankolay.trash.server.module.user.dto.UserDto
import dev.cankolay.trash.server.module.user.dto.request.CreateUserRequestDto
import dev.cankolay.trash.server.module.user.entity.toDto
import dev.cankolay.trash.server.module.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    private val controller: Controller,
    private val i18nService: I18nService,
    private val userService: UserService,
) {
    @PostMapping
    fun create(@RequestBody body: CreateUserRequestDto): ResponseEntity<ApiResponse<Nothing>> =
        controller {
            userService.create(email = body.email, username = body.username, password = body.password)

            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success"
                )
            )
        }

    @Authenticate
    @GetMapping
    fun get(): ResponseEntity<ApiResponse<UserDto>> =
        controller {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = userService.get().toDto()
                )
            )
        }

    @Authenticate
    @DeleteMapping
    fun delete(@RequestParam(value = "token") token: String): ResponseEntity<ApiResponse<Nothing>> =
        controller {
            userService.delete(jwt = token)

            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success"
                )
            )
        }
}
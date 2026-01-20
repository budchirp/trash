package dev.cankolay.trash.server.module.application.controller

import dev.cankolay.trash.server.common.model.ApiResponse
import dev.cankolay.trash.server.common.service.I18nService
import dev.cankolay.trash.server.common.util.Controller
import dev.cankolay.trash.server.module.application.dto.ApplicationDto
import dev.cankolay.trash.server.module.application.dto.request.CreateApplicationRequestDto
import dev.cankolay.trash.server.module.application.entity.toDto
import dev.cankolay.trash.server.module.application.service.ApplicationService
import dev.cankolay.trash.server.module.auth.annotation.Authenticate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/application")
class ApplicationController(
    private val controller: Controller,
    private val i18nService: I18nService,
    private val applicationService: ApplicationService
) {

    @Authenticate
    @PostMapping
    fun create(@RequestBody body: CreateApplicationRequestDto): ResponseEntity<ApiResponse<ApplicationDto>> =
        controller(permissions = listOf("application:create")) {


            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = applicationService.create(name = body.name, description = body.description).toDto()
                )
            )
        }

    @Authenticate
    @GetMapping("/all")
    fun getAll(): ResponseEntity<ApiResponse<List<ApplicationDto>>> =
        controller(permissions = listOf("application:read")) {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = applicationService.getAll().map { it.toDto() }
                )
            )
        }

    @Authenticate
    @GetMapping("/{id}")
    fun get(@PathVariable id: String): ResponseEntity<ApiResponse<ApplicationDto>> =
        controller(permissions = listOf("application:read")) {
            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success",
                    data = applicationService.get(applicationId = id).toDto()
                )
            )
        }

    @Authenticate
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<ApiResponse<Nothing>> =
        controller(permissions = listOf("application:delete")) {
            applicationService.delete(id)

            ResponseEntity.ok().body(
                ApiResponse(
                    message = i18nService.get("success"),
                    code = "success"
                )
            )
        }
}

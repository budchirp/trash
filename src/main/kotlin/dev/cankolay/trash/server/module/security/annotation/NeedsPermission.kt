package dev.cankolay.trash.server.module.security.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class NeedsPermission(vararg val permissions: String = ["user:read"])
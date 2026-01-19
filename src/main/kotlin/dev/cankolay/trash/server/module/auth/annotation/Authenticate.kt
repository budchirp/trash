package dev.cankolay.trash.server.module.auth.annotation

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class Authenticate
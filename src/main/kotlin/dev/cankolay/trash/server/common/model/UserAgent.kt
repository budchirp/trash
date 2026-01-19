package dev.cankolay.trash.server.common.model

enum class UserAgentPlatform {
    MOBILE, DESKTOP, TABLET
}

data class UserAgent(
    val device: String,
    val platform: UserAgentPlatform,

    val os: String,
    val browser: String,
)

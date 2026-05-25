package dev.cankolay.trash.server.common.service

import dev.cankolay.trash.server.common.model.JWTPayload
import dev.cankolay.trash.server.common.model.JwtPurpose
import dev.cankolay.trash.server.module.auth.entity.Token
import dev.cankolay.trash.server.module.auth.entity.TokenType
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JwtService(
    @param:Value("\${app.jwt.secret}")
    private val secret: String
) {
    private val key by lazy { Keys.hmacShaKeyFor(secret.toByteArray()) }

    fun extract(jwt: String?): String? =
        if (jwt != null && jwt.startsWith(prefix = "Bearer ")) jwt.removePrefix(prefix = "Bearer ") else null

    fun generateAccessToken(
        userId: String,
        token: Token,
        duration: Duration = Duration.ofDays(30)
    ): String = generate(
        userId = userId,
        token = token,
        purpose = JwtPurpose.ACCESS,
        duration = duration
    )

    fun generateSecurityVerificationToken(
        userId: String,
        duration: Duration = Duration.ofMinutes(15)
    ): String = generate(
        userId = userId,
        token = null,
        purpose = JwtPurpose.SECURITY_VERIFICATION,
        duration = duration
    )

    fun parse(jwt: String): JWTPayload? = runCatching {
        val claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).payload

        JWTPayload(
            user = claims[USER_CLAIM]?.toString().orEmpty(),
            id = claims[ID_CLAIM]?.toString(),
            type = claims[TYPE_CLAIM]?.toString()?.let(TokenType::valueOf),
            purpose = JwtPurpose.valueOf(claims[PURPOSE_CLAIM]?.toString().orEmpty())
        )
    }.getOrNull()?.takeIf { it.user.isNotBlank() }

    private fun generate(
        userId: String,
        token: Token?,
        purpose: JwtPurpose,
        duration: Duration
    ): String {
        val now = Instant.now()
        val builder = Jwts.builder()
            .claim(USER_CLAIM, userId)
            .claim(PURPOSE_CLAIM, purpose.name)

        if (token != null) {
            builder
                .claim(ID_CLAIM, token.id)
                .claim(TYPE_CLAIM, token.type.name)
        }

        return builder
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(duration)))
            .signWith(key)
            .compact()
    }

    companion object {
        private const val ID_CLAIM = "id"
        private const val USER_CLAIM = "user"
        private const val TYPE_CLAIM = "type"
        private const val PURPOSE_CLAIM = "purpose"
    }
}

package dev.cankolay.trash.server.common.service

import dev.cankolay.trash.server.common.model.JWTPayload
import dev.cankolay.trash.server.module.auth.entity.Token
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtService(
    @Value("\${app.jwt.secret}")
    private val secret: String
) {
    private fun key() = Keys.hmacShaKeyFor(secret.toByteArray())

    fun extract(jwt: String?): String? =
        if (jwt != null && jwt.startsWith(prefix = "Bearer ")) jwt.removePrefix(prefix = "Bearer ") else null

    fun generate(userId: String, token: Token? = null, duration: Long = 1000L * 60 * 60 * 24 * 30): String {
        val now = System.currentTimeMillis()

        val builder = Jwts.builder()
            .subject("user")
            .claim("id", userId)

        token?.let { builder.claim("token", it.id) }

        return builder
            .expiration(Date(now + duration))
            .issuedAt(Date(now))
            .signWith(key())
            .compact()
    }


    fun verify(jwt: String): Boolean = try {
        Jwts.parser().verifyWith(key()).build().parseSignedClaims(jwt)

        true
    } catch (_: Exception) {
        false
    }

    fun payload(jwt: String): JWTPayload {
        val payload = Jwts.parser().verifyWith(key()).build().parseSignedClaims(jwt).payload

        return JWTPayload(
            id = payload["id"].toString(),
            token = payload["token"].toString()
        )
    }

    fun getUserId(jwt: String): String {
        val payload = Jwts.parser().verifyWith(key()).build().parseSignedClaims(jwt).payload

        return payload["id"].toString()
    }
}
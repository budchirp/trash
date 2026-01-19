package dev.cankolay.trash.server.module.auth.repository

import dev.cankolay.trash.server.module.auth.entity.Token
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<Token, String>
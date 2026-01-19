package dev.cankolay.trash.server.module.session.repository

import dev.cankolay.trash.server.module.session.entity.Token
import org.springframework.data.jpa.repository.JpaRepository

interface TokenRepository : JpaRepository<Token, String>
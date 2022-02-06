package dev.martiniano.domain.entity

import dev.martiniano.application.security.UserRole
import dev.martiniano.domain.annotation.NoArg
import org.bson.types.ObjectId
import java.time.Clock
import java.time.LocalDateTime

@NoArg
data class User(
    var id: ObjectId? = null,
    var name: String,
    var email: String,
    var password: String,
    var roles: List<UserRole> = listOf(UserRole.ROLE_USER),
    var createdAt: LocalDateTime = LocalDateTime.now(Clock.systemUTC())
)

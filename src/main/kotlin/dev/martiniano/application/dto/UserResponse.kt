package dev.martiniano.application.dto

import dev.martiniano.application.security.UserRole
import dev.martiniano.domain.entity.User
import java.time.LocalDateTime

class UserResponse(
    val id: String,
    val name: String,
    val email: String,
    var roles: List<UserRole>,
    var createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(user: User): UserResponse =
            UserResponse(
                id = user.id.toString(),
                name = user.name,
                email = user.email,
                roles = user.roles,
                createdAt = user.createdAt
            )
    }
}

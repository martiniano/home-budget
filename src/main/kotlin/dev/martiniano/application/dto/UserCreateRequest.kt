package dev.martiniano.application.dto

import dev.martiniano.application.security.UserRole

class UserCreateRequest(
    val name: String,
    val email: String,
    val password: String,
    val roles: List<UserRole>?
)

class UserUpdateRequest(
    name: String
) {
    val name = name.trim()
}

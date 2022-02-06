package dev.martiniano.application.dto

import dev.martiniano.application.security.UserRole

class UserRequest(
    val name: String,
    val email: String,
    val password: String,
    var roles: List<UserRole> = listOf(UserRole.ROLE_USER)
)

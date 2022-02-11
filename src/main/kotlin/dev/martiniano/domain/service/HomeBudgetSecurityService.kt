package dev.martiniano.domain.service

import dev.martiniano.application.security.UserRole
import io.micronaut.security.utils.SecurityService
import jakarta.inject.Singleton

@Singleton
class HomeBudgetSecurityService(
    private val securityService: SecurityService
) {
    val id: String get() = securityService.authentication.get().attributes["id"] as String

    val username: String get() = securityService.username().get()

    val isAuthenticated: Boolean get() = securityService.isAuthenticated

    val isAdmin: Boolean get() = securityService.isAuthenticated && securityService.hasRole(UserRole.ROLE_ADMIN.name)
}

package dev.martiniano.domain.service

import dev.martiniano.application.dto.UserCreateRequest
import dev.martiniano.application.dto.UserUpdateRequest
import dev.martiniano.application.security.UserRole
import dev.martiniano.domain.entity.User
import dev.martiniano.domain.exception.BusinessException
import dev.martiniano.domain.exception.NotFoundException
import dev.martiniano.domain.logging.Loggable
import dev.martiniano.domain.logging.logger
import dev.martiniano.domain.repository.UserRepository
import jakarta.inject.Singleton
import org.bson.BsonValue
import org.jasypt.util.password.StrongPasswordEncryptor

@Singleton
class UserService(
    private val userRepository: UserRepository,
    private val strongPasswordEncryptor: StrongPasswordEncryptor,
    private val securityService: HomeBudgetSecurityService
) : Loggable {
    fun createUser(request: UserCreateRequest): BsonValue? {
        validateUserCreateRequest(request)

        val insertedUser = userRepository.create(
            User(
                name = request.name,
                email = request.email,
                password = strongPasswordEncryptor.encryptPassword(request.password),
                roles = request.roles ?: listOf(UserRole.ROLE_USER)
            )
        )
        return insertedUser.insertedId
    }

    fun findAll(name: String?): List<User> {
        return userRepository.findAll(name)
    }

    fun findById(id: String): User {
        return userRepository.findById(id)
            ?: throw NotFoundException("User with id $id was not found")
    }

    fun findByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw NotFoundException("User with email $email was not found")
    }

    fun updateUser(id: String, request: UserUpdateRequest): User {
        val currentUser = findById(id)
        validateUserUpdateRequest(currentUser, request)
        val updateUser = currentUser.copy(
            name = request.name
        )

        userRepository.update(
            id = id,
            update = updateUser
        ).also {
            if (it.modifiedCount == 0L)
                logger().info("User with id $id was not updated")
        }

        return updateUser
    }

    fun deleteById(id: String) {
        val deleteResult = userRepository.deleteById(id)
        if (deleteResult.deletedCount == 0L)
            throw NotFoundException("User with id $id was not deleted")
    }

    private fun validateUserCreateRequest(request: UserCreateRequest): Boolean {
        if (request.roles != null && request.roles.contains(UserRole.ROLE_ADMIN) && !securityService.isAdmin)
            throw BusinessException("Only admin can create another admin")

        if (request.name.isEmpty())
            throw BusinessException("Invalid value for name")

        return true
    }

    private fun validateUserUpdateRequest(user: User, request: UserUpdateRequest): Boolean {
        if (securityService.username != user.email && !securityService.isAdmin)
            throw BusinessException("Action not permitted")

        if (request.name.isEmpty())
            throw BusinessException("Invalid value for name")

        return true
    }
}

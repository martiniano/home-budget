package dev.martiniano.domain.service

import dev.martiniano.application.dto.UserRequest
import dev.martiniano.domain.entity.User
import dev.martiniano.domain.exception.NotFoundException
import dev.martiniano.domain.repository.UserRepository
import jakarta.inject.Singleton
import org.bson.BsonValue
import org.jasypt.util.password.StrongPasswordEncryptor

@Singleton
class UserService(
    private val userRepository: UserRepository,
    private val strongPasswordEncryptor: StrongPasswordEncryptor
) {
    fun createUser(request: UserRequest): BsonValue? {
        val insertedUser = userRepository.create(
            User(
                name = request.name,
                email = request.email,
                password = strongPasswordEncryptor.encryptPassword(request.password),
                roles = request.roles
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

    fun updateUser(id: String, request: UserRequest): User {
        val updateResult = userRepository.update(
            id = id,
            update = User(
                name = request.name,
                email = request.email,
                password = strongPasswordEncryptor.encryptPassword(request.password),
                roles = request.roles
            )
        )
        if (updateResult.modifiedCount == 0L)
            throw throw NotFoundException("User with id $id was not updated")
        return findById(id)
    }

    fun deleteById(id: String) {
        val deleteResult = userRepository.deleteById(id)
        if (deleteResult.deletedCount == 0L)
            throw throw NotFoundException("User with id $id was not deleted")
    }
}

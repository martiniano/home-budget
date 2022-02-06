package dev.martiniano.application.security

import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Prototype
import org.jasypt.util.password.StrongPasswordEncryptor

@Factory
class PasswordEncoder {

    @Prototype
    fun strongPasswordEncryptor(): StrongPasswordEncryptor {
        return StrongPasswordEncryptor()
    }
}

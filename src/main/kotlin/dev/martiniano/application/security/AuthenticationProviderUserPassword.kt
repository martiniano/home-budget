package dev.martiniano.application.security

import dev.martiniano.domain.exception.NotFoundException
import dev.martiniano.domain.service.UserService
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Singleton
import org.jasypt.util.password.StrongPasswordEncryptor
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class AuthenticationProviderUserPassword(
    private val userService: UserService,
    private val strongPasswordEncryptor: StrongPasswordEncryptor
) : AuthenticationProvider {
    override fun authenticate(
        httpRequest: HttpRequest<*>?,
        authenticationRequest: AuthenticationRequest<*, *>
    ): Publisher<AuthenticationResponse> {
        return Flux.create({ emitter: FluxSink<AuthenticationResponse> ->
            try {
                userService.findByEmail(authenticationRequest.identity as String).let { user ->
                    if (strongPasswordEncryptor.checkPassword(authenticationRequest.secret as String, user.password)) {
                        emitter.next(
                            AuthenticationResponse.success(
                                authenticationRequest.identity as String,
                                user.roles.map { it.name }
                            )
                        )
                        emitter.complete()
                    } else {
                        emitter.error(AuthenticationResponse.exception("Username or Password invalid"))
                    }
                }
            } catch (ex: NotFoundException) {
                emitter.error(AuthenticationResponse.exception("Username ou Password invalid"))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}

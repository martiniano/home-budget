package dev.martiniano.application.security

import dev.martiniano.domain.entity.RefreshToken
import dev.martiniano.domain.repository.RefreshTokenRepository
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.errors.IssuingAnAccessTokenErrorCode.INVALID_GRANT
import io.micronaut.security.errors.OauthErrorResponseException
import io.micronaut.security.token.event.RefreshTokenGeneratedEvent
import io.micronaut.security.token.refresh.RefreshTokenPersistence
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class CustomRefreshTokenPersistence(private val refreshTokenRepository: RefreshTokenRepository) :
    RefreshTokenPersistence {
    override fun persistToken(event: RefreshTokenGeneratedEvent?) {
        if (event?.refreshToken != null && event.authentication?.name != null) {
            val payload = event.refreshToken

            refreshTokenRepository.create(
                RefreshToken(
                    username = event.authentication.name,
                    refreshToken = payload,
                    revoked = false
                )
            )
        }
    }

    override fun getAuthentication(refreshToken: String): Publisher<Authentication> {
        return Flux.create({ emitter: FluxSink<Authentication> ->
            refreshTokenRepository.findByRefreshToken(refreshToken)?.let { tokenOpt ->
                val (_, username, _, revoked) = tokenOpt
                if (revoked) {
                    emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token revoked", null))
                } else {
                    emitter.next(Authentication.build(username))
                    emitter.complete()
                }
            } ?: emitter.error(OauthErrorResponseException(INVALID_GRANT, "refresh token not found", null))
        }, FluxSink.OverflowStrategy.ERROR)
    }
}

package dev.martiniano.application.security

import io.micronaut.context.annotation.Requires
import io.micronaut.context.env.Environment
import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import io.micronaut.security.authentication.AuthenticationResponse
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator
import org.reactivestreams.Publisher

@Filter("/**")
@Requires(env = [Environment.TEST])
class MockSecurityByPassFilter(
    private val jwtTokenGenerator: JwtTokenGenerator
) : HttpClientFilter {

    override fun doFilter(request: MutableHttpRequest<*>, chain: ClientFilterChain): Publisher<out HttpResponse<*>> {
        if (request.uri.path != "/login") {
            val token = jwtTokenGenerator.generateToken(
                AuthenticationResponse.success("sherlock").authentication.get(),
                3600
            )
            request.bearerAuth(token.get())
        }
        return chain.proceed(request)
    }
}

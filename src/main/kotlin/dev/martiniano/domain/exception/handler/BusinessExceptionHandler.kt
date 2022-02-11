package dev.martiniano.domain.exception.handler

import dev.martiniano.domain.exception.BusinessException
import dev.martiniano.domain.exception.ExceptionResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Produces
import io.micronaut.http.server.exceptions.ExceptionHandler
import jakarta.inject.Singleton

@Produces
@Singleton
class BusinessExceptionHandler : ExceptionHandler<BusinessException, HttpResponse<ExceptionResponse>> {
    override fun handle(
        request: HttpRequest<*>,
        exception: BusinessException
    ): HttpResponse<ExceptionResponse> =
        HttpResponse.badRequest(
            ExceptionResponse(
                message = exception.message
            )
        )
}

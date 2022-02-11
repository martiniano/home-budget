package dev.martiniano.application.controller

import dev.martiniano.application.dto.UserCreateRequest
import dev.martiniano.application.dto.UserResponse
import dev.martiniano.application.dto.UserUpdateRequest
import dev.martiniano.domain.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.net.URI

@Controller("user")
class UserController(private val userService: UserService) {

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post
    fun create(@Body request: UserCreateRequest): HttpResponse<Void> {
        val createdId = userService.createUser(request)
        return HttpResponse.created(
            URI.create(
                createdId!!.asObjectId().value.toHexString()
            )
        )
    }

    @Secured("ROLE_ADMIN")
    @Get
    fun findAll(@QueryValue("description") description: String?): HttpResponse<List<UserResponse>> {
        val companies = userService
            .findAll(description)
            .map { UserResponse.fromEntity(it) }
        return HttpResponse.ok(companies)
    }

    @Secured("ROLE_ADMIN")
    @Get("/{id}")
    fun findById(@PathVariable id: String): HttpResponse<UserResponse> {
        val user = userService.findById(id)
        return HttpResponse.ok(
            UserResponse.fromEntity(user)
        )
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Put("/{id}")
    fun update(
        @PathVariable id: String,
        @Body request: UserUpdateRequest
    ): HttpResponse<UserResponse> {
        val updatedUser = userService.updateUser(id, request)
        return HttpResponse.ok(
            UserResponse.fromEntity(updatedUser)
        )
    }

    @Secured("ROLE_ADMIN")
    @Delete("/{id}")
    fun deleteById(@PathVariable id: String): HttpResponse<Void> {
        userService.deleteById(id)
        return HttpResponse.noContent()
    }
}

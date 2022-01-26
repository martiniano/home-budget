package dev.martiniano.client

import dev.martiniano.application.dto.IncomeRequest
import dev.martiniano.application.dto.IncomeResponse
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import javax.validation.Valid
import javax.validation.constraints.NotNull


@Client("/income")
interface IncomeClient {
    @Post
    @NonNull
    fun save(@NonNull fruit: @NotNull @Valid IncomeRequest?): HttpStatus?

    @NonNull
    @Get
    fun findAll(): List<IncomeResponse>
}
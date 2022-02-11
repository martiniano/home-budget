package dev.martiniano.client

import dev.martiniano.application.dto.IncomeRequest
import dev.martiniano.application.dto.IncomeResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import javax.validation.Valid
import javax.validation.constraints.NotNull

@Client("/income")
interface IncomeClient {

    @Post
    fun save(@Body incomeRequest: @NotNull @Valid IncomeRequest): HttpStatus

    @Get
    fun findAll(@QueryValue description: String? = null): List<IncomeResponse>

    @Get
    fun findById(@PathVariable id: @Valid String): IncomeResponse

    @Get
    fun findAllInMonth(@PathVariable year: Int, @PathVariable month: Int): List<IncomeResponse>

    @Put
    fun update(@PathVariable id: String, incomeRequest: @Valid IncomeRequest?): IncomeResponse

    @Delete
    fun deleteById(@PathVariable id: String): HttpStatus?
}

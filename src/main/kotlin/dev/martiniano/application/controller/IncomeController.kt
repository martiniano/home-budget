package dev.martiniano.application.controller

import dev.martiniano.application.dto.IncomeRequest
import dev.martiniano.application.dto.IncomeResponse
import dev.martiniano.domain.service.IncomeService
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

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("income")
class IncomeController(private val incomeService: IncomeService) {

    @Post
    fun create(@Body request: IncomeRequest): HttpResponse<Void> {
        val createdId = incomeService.createIncome(request)
        return HttpResponse.created(
            URI.create(
                createdId!!.asObjectId().value.toHexString()
            )
        )
    }

    @Get
    fun findAll(@QueryValue("description") description: String?): HttpResponse<List<IncomeResponse>> {
        val companies = incomeService
            .findAll(description)
            .map { IncomeResponse.fromEntity(it) }
        return HttpResponse.ok(companies)
    }

    @Get("/{year}/{month}")
    fun findAllInMonth(@PathVariable year: Int, @PathVariable month: Int): HttpResponse<List<IncomeResponse>> {
        val companies = incomeService
            .findAllInMonth(year, month)
            .map { IncomeResponse.fromEntity(it) }
        return HttpResponse.ok(companies)
    }

    @Get("/{id}")
    fun findById(@PathVariable id: String): HttpResponse<IncomeResponse> {
        val income = incomeService.findById(id)
        return HttpResponse.ok(
            IncomeResponse.fromEntity(income)
        )
    }

    // TODO("Check if id exists")
    @Put("/{id}")
    fun update(
        @PathVariable id: String,
        @Body request: IncomeRequest
    ): HttpResponse<IncomeResponse> {
        val updatedIncome = incomeService.updateIncome(id, request)
        return HttpResponse.ok(
            IncomeResponse.fromEntity(updatedIncome)
        )
    }
    @Delete("/{id}")
    fun deleteById(@PathVariable id: String): HttpResponse<Void> {
        incomeService.deleteById(id)
        return HttpResponse.noContent()
    }
}

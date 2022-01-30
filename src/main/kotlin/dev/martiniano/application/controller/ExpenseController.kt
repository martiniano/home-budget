package dev.martiniano.application.controller

import dev.martiniano.application.dto.ExpenseRequest
import dev.martiniano.application.dto.ExpenseResponse
import dev.martiniano.domain.service.ExpenseService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import java.net.URI

@Controller("expense")
class ExpenseController(private val expenseService: ExpenseService) {

    @Post
    fun create(@Body request: ExpenseRequest): HttpResponse<Void> {
        val createdId = expenseService.createExpense(request)
        return HttpResponse.created(
            URI.create(
                createdId!!.asObjectId().value.toHexString()
            )
        )
    }

    @Get
    fun findAll(@QueryValue("description") description: String?): HttpResponse<List<ExpenseResponse>> {
        val companies = expenseService
            .findAll(description)
            .map { ExpenseResponse.fromEntity(it) }
        return HttpResponse.ok(companies)
    }

    @Get("/{year}/{month}")
    fun findAllInMonth(@PathVariable year: Int, @PathVariable month: Int): HttpResponse<List<ExpenseResponse>> {
        val companies = expenseService
            .findAllInMonth(year, month)
            .map { ExpenseResponse.fromEntity(it) }
        return HttpResponse.ok(companies)
    }

    @Get("/{id}")
    fun findById(@PathVariable id: String): HttpResponse<ExpenseResponse> {
        val expense = expenseService.findById(id)
        return HttpResponse.ok(
            ExpenseResponse.fromEntity(expense)
        )
    }

    // TODO("Check if id exists")
    @Put("/{id}")
    fun update(
        @PathVariable id: String,
        @Body request: ExpenseRequest
    ): HttpResponse<ExpenseResponse> {
        val updatedExpense = expenseService.updateExpense(id, request)
        return HttpResponse.ok(
            ExpenseResponse.fromEntity(updatedExpense)
        )
    }
    @Delete("/{id}")
    fun deleteById(@PathVariable id: String): HttpResponse<Void> {
        expenseService.deleteById(id)
        return HttpResponse.noContent()
    }
}

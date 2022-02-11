package dev.martiniano.domain.service

import dev.martiniano.application.dto.ExpenseRequest
import dev.martiniano.domain.entity.Expense
import dev.martiniano.domain.exception.NotFoundException
import dev.martiniano.domain.logging.Loggable
import dev.martiniano.domain.logging.logger
import dev.martiniano.domain.repository.ExpenseRepository
import jakarta.inject.Singleton
import org.bson.BsonValue
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth

@Singleton
class ExpenseService(
    private val expenseRepository: ExpenseRepository
) : Loggable {
    fun createExpense(request: ExpenseRequest): BsonValue? {
        val insertedExpense = expenseRepository.create(
            Expense(
                description = request.description,
                userId = expenseRepository.security.id,
                amount = request.amount,
                category = request.category,
                date = request.date
            )
        )
        return insertedExpense.insertedId
    }

    fun findAll(description: String?): List<Expense> {
        return expenseRepository.findAll(description)
    }

    fun findAllInMonth(year: Int, month: Int): List<Expense> {
        val yearMonth = YearMonth.of(year, month)
        val startDateTime = yearMonth.atDay(1).atStartOfDay()
        val endDateTime = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX)
        return expenseRepository.findAllInPeriod(startDateTime, endDateTime)
    }

    fun findById(id: String): Expense {
        return expenseRepository.findById(id)
            ?: throw NotFoundException("Expense with id $id was not found")
    }

    fun updateExpense(id: String, request: ExpenseRequest): Expense {
        val currentExpense = findById(id)
        val updateExpense = currentExpense.copy(
            description = request.description,
            userId = expenseRepository.security.id,
            amount = request.amount,
            category = request.category,
            date = request.date
        )

        expenseRepository.update(
            id = id,
            update = updateExpense
        ).also {
            if (it.modifiedCount == 0L)
                logger().info("Expense with id $id was not updated")
        }

        return updateExpense
    }

    fun deleteById(id: String) {
        val deleteResult = expenseRepository.deleteById(id)
        if (deleteResult.deletedCount == 0L)
            throw throw NotFoundException("Expense with id $id was not deleted")
    }
}

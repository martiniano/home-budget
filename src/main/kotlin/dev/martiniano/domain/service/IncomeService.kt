package dev.martiniano.domain.service

import dev.martiniano.application.dto.IncomeRequest
import dev.martiniano.domain.entity.Income
import dev.martiniano.domain.exception.NotFoundException
import dev.martiniano.domain.repository.IncomeRepository
import jakarta.inject.Singleton
import org.bson.BsonValue
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth

@Singleton
class IncomeService(private val incomeRepository: IncomeRepository) {
    fun createIncome(request: IncomeRequest): BsonValue? {
        val insertedIncome = incomeRepository.create(
            Income(
                description = request.description,
                amount = request.amount,
                category = request.category,
                date = request.date
            )
        )
        return insertedIncome.insertedId
    }

    fun findAll(description: String?):  List<Income> {
        return incomeRepository.findAll(description)
    }

    fun findAllInMonth(year: Int, month: Int): List<Income> {
        val yearMonth = YearMonth.of(year, month)
        val startDateTime = yearMonth.atDay(1).atStartOfDay()
        val endDateTime =  LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX);
        return incomeRepository.findAllInPeriod(startDateTime, endDateTime)
    }

    fun findById(id: String): Income {
        return incomeRepository.findById(id)
            ?: throw NotFoundException("Income with id $id was not found")
    }

    fun updateIncome(id: String, request: IncomeRequest): Income {
        val updateResult = incomeRepository.update(
            id = id,
            update = Income(
                description = request.description,
                amount = request.amount,
                date = request.date
            )
        )
        if (updateResult.modifiedCount == 0L)
            throw throw RuntimeException("Income with id $id was not updated")
        return findById(id)
    }

    fun deleteById(id: String) {
        val deleteResult = incomeRepository.deleteById(id)
        if (deleteResult.deletedCount == 0L)
            throw throw RuntimeException("Income with id $id was not deleted")
    }
}
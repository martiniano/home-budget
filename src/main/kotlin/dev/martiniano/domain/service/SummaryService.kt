package dev.martiniano.domain.service

import dev.martiniano.domain.entity.Summary
import dev.martiniano.domain.repository.ExpenseRepository
import dev.martiniano.domain.repository.IncomeRepository
import jakarta.inject.Singleton
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth

@Singleton
class SummaryService(
    private val incomeRepository: IncomeRepository,
    private val expenseRepository: ExpenseRepository
) {

    fun summarize(year: Int, month: Int): Summary {
        val yearMonth = YearMonth.of(year, month)
        val startDateTime = yearMonth.atDay(1).atStartOfDay()
        val endDateTime = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX)
        val incomeCategoriesTotal = incomeRepository.sumCategoriesAmountInPeriod(startDateTime, endDateTime)
        val expenseCategoriesTotal = expenseRepository.sumCategoriesAmountInPeriod(startDateTime, endDateTime)
        val incomeTotal = incomeCategoriesTotal.values.sum()
        val expenseTotal = expenseCategoriesTotal.values.sum()
        return Summary(
            income = incomeTotal,
            expense = expenseTotal,
            balance = incomeTotal - expenseTotal,
            incomeCategories = incomeCategoriesTotal,
            expenseCategories = expenseCategoriesTotal
        )
    }
}

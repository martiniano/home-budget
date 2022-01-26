package dev.martiniano.application.dto

import dev.martiniano.domain.entity.Income
import dev.martiniano.domain.enum.IncomeCategory
import java.time.LocalDateTime

class IncomeResponse(
    val id: String,
    val description: String,
    val amount: Double,
    val category: IncomeCategory,
    val date: LocalDateTime
) {
    companion object {
        fun fromEntity(income: Income): IncomeResponse =
            IncomeResponse(
                id = income.id.toString(),
                description = income.description,
                amount = income.amount,
                category = income.category,
                date = income.date
            )
    }
}
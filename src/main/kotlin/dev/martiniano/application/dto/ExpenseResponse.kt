package dev.martiniano.application.dto

import dev.martiniano.domain.entity.Expense
import dev.martiniano.domain.enum.ExpenseCategory
import java.time.LocalDateTime

class ExpenseResponse(
    val id: String,
    val description: String,
    val amount: Double,
    var category: ExpenseCategory,
    val date: LocalDateTime
) {
    companion object {
        fun fromEntity(expense: Expense): ExpenseResponse =
            ExpenseResponse(
                id = expense.id.toString(),
                description = expense.description,
                amount = expense.amount,
                category = expense.category,
                date = expense.date
            )
    }
}

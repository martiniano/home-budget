package dev.martiniano.application.dto

import dev.martiniano.domain.entity.Summary

class SummaryResponse(
    val income: Double,
    val expense: Double,
    val balance: Double,
    val incomeCategories: Map<String, Double>,
    val expenseCategories: Map<String, Double>
) {
    companion object {
        fun fromEntity(summary: Summary): SummaryResponse =
            SummaryResponse(
                income = summary.income,
                expense = summary.expense,
                balance = summary.balance,
                incomeCategories = summary.incomeCategories,
                expenseCategories = summary.expenseCategories
            )
    }
}
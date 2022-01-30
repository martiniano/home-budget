package dev.martiniano.domain.entity

import dev.martiniano.domain.annotation.NoArg

@NoArg
data class Summary(
    val income: Double,
    val expense: Double,
    val balance: Double,
    val incomeCategories: Map<String, Double>,
    val expenseCategories: Map<String, Double>
)

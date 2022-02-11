package dev.martiniano.domain.entity

import dev.martiniano.domain.annotation.NoArg
import dev.martiniano.domain.enum.ExpenseCategory
import org.bson.types.ObjectId
import java.time.Clock
import java.time.LocalDateTime

@NoArg
data class Expense(
    var id: ObjectId? = null,
    var userId: String,
    var description: String,
    var amount: Double,
    var category: ExpenseCategory = ExpenseCategory.OTHERS,
    var date: LocalDateTime = LocalDateTime.now(Clock.systemUTC())
)

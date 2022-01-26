package dev.martiniano.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import dev.martiniano.domain.enum.ExpenseCategory
import java.time.LocalDateTime

class ExpenseRequest(
    val description: String,
    val amount: Double,
    var category: ExpenseCategory = ExpenseCategory.OTHERS,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime
)
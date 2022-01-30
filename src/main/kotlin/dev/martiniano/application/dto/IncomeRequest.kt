package dev.martiniano.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import dev.martiniano.domain.enum.IncomeCategory
import java.time.LocalDateTime

class IncomeRequest(
    val description: String,
    val amount: Double,
    var category: IncomeCategory = IncomeCategory.OTHERS,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val date: LocalDateTime
)

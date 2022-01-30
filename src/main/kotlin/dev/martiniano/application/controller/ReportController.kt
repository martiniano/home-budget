package dev.martiniano.application.controller

import dev.martiniano.application.dto.SummaryResponse
import dev.martiniano.domain.service.SummaryService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable

@Controller
class ReportController(private val summaryService: SummaryService) {

    @Get("summarize/{year}/{month}")
    fun summarize(@PathVariable year: Int, @PathVariable month: Int): HttpResponse<SummaryResponse> {
        val companies = summaryService
            .summarize(year, month)
            .let { SummaryResponse.fromEntity(it) }
        return HttpResponse.ok(companies)
    }
}

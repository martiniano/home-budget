package dev.martiniano.application.controller

import dev.martiniano.application.config.MongoDbUtils
import dev.martiniano.application.dto.IncomeRequest
import dev.martiniano.application.dto.IncomeResponse
import dev.martiniano.client.IncomeClient
import dev.martiniano.domain.enum.IncomeCategory
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import jakarta.inject.Inject
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import java.time.LocalDateTime

@MicronautTest
@TestInstance(Lifecycle.PER_CLASS)
class IncomeControllerTest : TestPropertyProvider {

    @Inject
    lateinit var incomeClient: IncomeClient

    @Test
    fun saveIncome() {
        var incomes: List<IncomeResponse?>? = incomeClient.findAll()
        assertTrue(incomes!!.isEmpty())

        var status: HttpStatus? = incomeClient.save(
            IncomeRequest(
                description = "Base Salary",
                amount = 10000.0,
                category = IncomeCategory.SALARY,
                date = LocalDateTime.now()
            )
        )
        assertEquals(HttpStatus.CREATED, status)

        incomes = incomeClient.findAll()
        assertFalse(incomes.isEmpty())
        assertEquals("Base Salary", incomes[0].description)
        assertEquals(10000.0, incomes[0].amount)

        status = incomeClient.save(
            IncomeRequest(
                description = "Bank Loan",
                amount = 1200.0,
                date = LocalDateTime.now()
            )
        )
        assertEquals(HttpStatus.CREATED, status)

        incomes = incomeClient.findAll()
        assertEquals(2, incomes.count())
        assertTrue(
            incomes.stream()
                .filter { f: IncomeResponse -> f.description == "Bank Loan" }
                .findFirst()
                .isPresent
        )
    }

    companion object {
        @AfterAll
        fun cleanup() {
            MongoDbUtils.closeMongoDb()
        }
    }

    override fun getProperties(): Map<String, String> {
        MongoDbUtils.startMongoDb()
        return mapOf("mongodb.uri" to MongoDbUtils.mongoDbUri)
    }

    /*
    @Test
    fun testIncomeController() {
        val handler = MicronautLambdaHandler()
        val handlerInput = HandlerInput()
        handlerInput.message = "Hello"
        val objectMapper = handler.applicationContext.getBean(ObjectMapper::class.java)
        val json = objectMapper.writeValueAsString(handlerInput)
        val request = AwsProxyRequestBuilder("/convert", HttpMethod.POST.toString())
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
            .body(json)
            .build()
        val lambdaContext: Context = MockLambdaContext()
        val response = handler.handleRequest(request, lambdaContext)
        assertEquals(response.statusCode, HttpStatus.OK.code)
        val handlerOutput: HandlerOutput = objectMapper.readValue(response.body, HandlerOutput::class.java)
        assertNotNull(handlerOutput)
        assertEquals(handlerOutput.message, handlerInput.message)
        assertEquals(handlerOutput.pirateMessage, "Ahoy!")
        handler.applicationContext.close()
    }
     */
}

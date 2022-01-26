package dev.martiniano.application.controller

import dev.martiniano.application.config.MongoDbUtils
import dev.martiniano.application.dto.IncomeRequest
import dev.martiniano.application.dto.IncomeResponse
import dev.martiniano.client.IncomeClient
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.micronaut.test.support.TestPropertyProvider
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalDateTime

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IncomeControllerTest: TestPropertyProvider {

    @Test
    fun fruitsEndpointInteractsWithMongo(incomeClient: IncomeClient) {
        var incomes: List<IncomeResponse?>? = incomeClient.findAll()
        assertTrue(incomes!!.isEmpty())

        var status: HttpStatus? = incomeClient.save(
            IncomeRequest(
                description = "Phone bill",
                amount = 20.0,
                date = LocalDateTime.now()
            )
        )
        assertEquals(HttpStatus.CREATED, status)

        incomes = incomeClient.findAll()
        assertFalse(incomes.isEmpty())
        assertEquals("Phone bill", incomes[0].description)

        status = incomeClient.save(
            IncomeRequest(
                description = "Eletric bill",
                amount = 20.0,
                date = LocalDateTime.now()
            )
        )
        assertEquals(HttpStatus.CREATED, status)

        incomes = incomeClient.findAll()
        assertTrue(
            incomes.stream()
                .filter { f: IncomeResponse -> f.description == "Eletric bill" }
                .findFirst()
                .isPresent
        )
    }

    @AfterAll
    fun cleanup() {
        MongoDbUtils.closeMongoDb()
    }

    override fun getProperties(): Map<String, String> {
        MongoDbUtils.startMongoDb();
        return mapOf("mongodb.uri" to MongoDbUtils.mongoDbUri);
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
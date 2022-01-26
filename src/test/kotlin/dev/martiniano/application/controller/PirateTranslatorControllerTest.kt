package dev.martiniano.application.controller
import com.amazonaws.serverless.proxy.internal.testutils.AwsProxyRequestBuilder
import com.amazonaws.serverless.proxy.internal.testutils.MockLambdaContext
import com.amazonaws.services.lambda.runtime.Context
import com.fasterxml.jackson.databind.ObjectMapper
import dev.martiniano.application.handler.HandlerInput
import io.micronaut.function.aws.proxy.MicronautLambdaHandler
import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PirateTranslatorControllerTest {

//    @Test
//    fun testPirateTranslatorController() {
//        val handler = MicronautLambdaHandler()
//        val handlerInput = HandlerInput()
//        handlerInput.message = "Hello"
//        val objectMapper = handler.applicationContext.getBean(ObjectMapper::class.java)
//        val json = objectMapper.writeValueAsString(handlerInput)
//        val request = AwsProxyRequestBuilder("/convert", HttpMethod.POST.toString())
//            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//            .body(json)
//            .build()
//        val lambdaContext: Context = MockLambdaContext()
//        val response = handler.handleRequest(request, lambdaContext)
//        Assertions.assertEquals(response.statusCode, HttpStatus.OK.code)
//        val handlerOutput: HandlerOutput = objectMapper.readValue(response.body, HandlerOutput::class.java)
//        Assertions.assertNotNull(handlerOutput)
//        Assertions.assertEquals(handlerOutput.message, handlerInput.message)
//        Assertions.assertEquals(handlerOutput.pirateMessage, "Ahoy!")
//        handler.applicationContext.close()
//    }
}
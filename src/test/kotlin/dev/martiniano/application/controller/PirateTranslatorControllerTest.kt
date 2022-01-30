package dev.martiniano.application.controller

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

package dev.martiniano.application.handler

import io.micronaut.core.annotation.Introspected
import io.micronaut.function.aws.MicronautRequestHandler

@Introspected
class SampleRequestHandler : MicronautRequestHandler<HandlerInput?, HandlerOutput>() {

    val replacements = mapOf("Hello" to "Ahoy!", "Yes" to "Aye!", "Yes, Captain!" to "Aye Aye!")

    override fun execute(input: HandlerInput?): HandlerOutput {
        input?.let {
            return HandlerOutput(it.message, translate(it.message))
        }
        return HandlerOutput("", "");
    }

    fun translate(message: String): String {
        var result = message
        replacements.forEach { k, v -> result = result.replace(k, v) }
        return result
    }
}
package dev.martiniano.application.handler

import io.micronaut.core.annotation.Introspected

@Introspected
//data class HandlerInput(val message: String)
class HandlerInput {
    var message: String = ""
}
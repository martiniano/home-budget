package dev.martiniano.application

import io.micronaut.runtime.Micronaut.build

fun main(vararg args: String) {
    build()
        .args(*args)
        .packages("dev.martiniano")
        .start()
}

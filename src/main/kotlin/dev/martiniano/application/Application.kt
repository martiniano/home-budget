package dev.martiniano.application

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("dev.martiniano")
        .start()
}
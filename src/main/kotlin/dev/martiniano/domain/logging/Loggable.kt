package dev.martiniano.domain.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger

interface Loggable

inline fun <reified T : Loggable> T.logger(): Logger =
    getLogger(T::class.java)

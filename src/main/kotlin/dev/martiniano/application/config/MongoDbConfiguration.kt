package dev.martiniano.application.config

import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.core.naming.Named

@ConfigurationProperties("db")
interface MongoDbConfiguration : Named

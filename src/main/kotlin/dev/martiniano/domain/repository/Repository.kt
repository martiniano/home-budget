package dev.martiniano.domain.repository

import com.mongodb.client.MongoClient
import dev.martiniano.application.config.MongoDbConfiguration
import dev.martiniano.domain.service.HomeBudgetSecurityService

interface Repository {
    val mongoClient: MongoClient
    val mongoConf: MongoDbConfiguration
    val security: HomeBudgetSecurityService
}

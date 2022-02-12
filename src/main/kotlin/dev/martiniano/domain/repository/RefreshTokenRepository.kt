package dev.martiniano.domain.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates.combine
import com.mongodb.client.model.Updates.set
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import dev.martiniano.application.config.MongoDbConfiguration
import dev.martiniano.domain.entity.RefreshToken
import dev.martiniano.domain.entity.User
import jakarta.inject.Singleton
import org.bson.Document
import org.bson.conversions.Bson
import org.bson.types.ObjectId

@Singleton
class RefreshTokenRepository(private val mongoClient: MongoClient, private val mongoConf: MongoDbConfiguration) {

    fun create(refreshToken: RefreshToken): InsertOneResult =
        getCollection()
            .insertOne(refreshToken)

    fun findByRefreshToken(refreshToken: String): RefreshToken? =
        getCollection()
            .find(
                Filters.eq("refreshToken", refreshToken)
            )
            .toList()
            .firstOrNull()

    fun updateByUsername(username: String, refreshToken: RefreshToken): UpdateResult =
        getCollection()
            .replaceOne(
                Filters.eq("username", username) as Bson,
                refreshToken
            )

    private fun getCollection() =
        mongoClient.getDatabase(mongoConf.name)
            .getCollection("refresh_token", RefreshToken::class.java)
}

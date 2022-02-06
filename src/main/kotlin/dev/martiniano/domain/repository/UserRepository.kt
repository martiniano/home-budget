package dev.martiniano.domain.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import dev.martiniano.application.config.MongoDbConfiguration
import dev.martiniano.domain.entity.User
import jakarta.inject.Singleton
import org.bson.types.ObjectId

@Singleton
class UserRepository(private val mongoClient: MongoClient, private val mongoConf: MongoDbConfiguration) {

    fun create(user: User): InsertOneResult =
        getCollection()
            .insertOne(user)

    fun findAll(name: String?): List<User> =
        if (name != null)
            getCollection().find(Filters.regex("name", ".*$name.*")).toList()
        else
            getCollection().find().toList()

    fun findById(id: String): User? =
        getCollection()
            .find(
                Filters.eq("_id", ObjectId(id))
            )
            .toList()
            .firstOrNull()

    fun findByEmail(email: String): User? =
        getCollection()
            .find(
                Filters.eq("email", email)
            )
            .toList()
            .firstOrNull()

    fun update(id: String, update: User): UpdateResult =
        getCollection()
            .replaceOne(
                Filters.eq("_id", ObjectId(id)),
                update
            )

    fun deleteById(id: String): DeleteResult =
        getCollection()
            .deleteOne(
                Filters.eq("_id", ObjectId(id))
            )

    private fun getCollection() =
        mongoClient.getDatabase(mongoConf.name)
            .getCollection("user", User::class.java)
}

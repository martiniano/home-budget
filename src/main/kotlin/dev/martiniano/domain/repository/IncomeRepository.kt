package dev.martiniano.domain.repository

import com.mongodb.client.MongoClient
import com.mongodb.client.model.Accumulators
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import dev.martiniano.application.config.MongoDbConfiguration
import dev.martiniano.domain.entity.Income
import jakarta.inject.Singleton
import org.bson.Document
import org.bson.types.ObjectId
import java.time.LocalDateTime


@Singleton
class IncomeRepository(private val mongoClient: MongoClient, private val mongoConf: MongoDbConfiguration) {

    fun create(income: Income): InsertOneResult =
        getCollection()
            .insertOne(income)

    fun findAll(description: String?): List<Income> =
        if(description != null)
            getCollection().find(Filters.regex("description", ".*$description.*")).toList()
        else
            getCollection().find().toList()

    fun findAllInPeriod(startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<Income> =
            getCollection().find(Filters.and(
                Filters.gte("date", startDateTime),
                Filters.lte("date", endDateTime)
            )).toList()

    fun sumAmountInPeriod(startDateTime: LocalDateTime, endDateTime: LocalDateTime): Double =
        getCollection().aggregate(
            listOf(
                Aggregates.match(Filters.and(Filters.gte("date", startDateTime), Filters.lte("date", endDateTime))),
                Aggregates.group(null, Accumulators.sum("amount", "\$amount")),
            ),
            Document::class.java
        ).first()?.getDouble("amount") ?: 0.0

    fun sumCategoriesAmountInPeriod(startDateTime: LocalDateTime, endDateTime: LocalDateTime): Map<String, Double> =
        getCollection().aggregate(
            listOf(
                Aggregates.match(Filters.and(Filters.gte("date", startDateTime), Filters.lte("date", endDateTime))),
                Aggregates.group("\$category", Accumulators.sum("amount", "\$amount")),
            ),
            Document::class.java
        ).toList().associate { it.getString("_id") to it.getDouble("amount") }

    fun findById(id: String): Income? =
        getCollection()
            .find(
                Filters.eq("_id", ObjectId(id))
            )
            .toList()
            .firstOrNull()

    fun update(id: String, update: Income): UpdateResult =
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
        mongoClient
            .getDatabase(mongoConf.name)
            .getCollection("income", Income::class.java)
}
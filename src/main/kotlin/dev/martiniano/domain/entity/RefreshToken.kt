package dev.martiniano.domain.entity

import dev.martiniano.domain.annotation.NoArg
import org.bson.types.ObjectId
import java.time.Instant

@NoArg
data class RefreshToken(
    var id: ObjectId? = null,
    var username: String,
    var refreshToken: String,
    var revoked: Boolean,
    var createdAt: Instant? = null
)

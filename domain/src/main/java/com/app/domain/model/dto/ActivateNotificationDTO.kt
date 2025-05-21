package com.app.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject

@Serializable
data class ActivateNotificationDTO(
    @SerialName("user_id")
    val userId: String = "",

    @SerialName("feed")
    val feed: Int = 0,

    @SerialName("km")
    val km: Double = 0.0,

    @SerialName("crew_id")
    val crewId: JsonObject = buildJsonObject {  },

    @SerialName("user_name")
    val userName: String = "",

    @SerialName("is_public")
    val isPublic: Boolean = true,

    @SerialName("created_at")
    val createdAt: String = ""
)
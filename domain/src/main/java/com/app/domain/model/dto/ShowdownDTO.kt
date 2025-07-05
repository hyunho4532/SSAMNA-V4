package com.app.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject

@Serializable
data class ShowdownDTO(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("user_id")
    val userId: String = "",

    @SerialName("other_id")
    val otherId: String = "",

    @SerialName("names")
    val names: JsonArray,

    @SerialName("user_steps")
    val userSteps: Int? = 0,

    @SerialName("other_steps")
    val otherSteps: Int? = 0
)
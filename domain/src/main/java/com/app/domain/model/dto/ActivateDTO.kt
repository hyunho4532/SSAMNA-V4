package com.app.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject

@Serializable
data class ActivateDTO(

    @SerialName("id")
    val id: Int = 0,

    @SerialName("user_id")
    val userId: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("coord")
    val coord: JsonObject = buildJsonObject {  },

    @SerialName("status")
    val status: JsonObject = buildJsonObject {  },

    @SerialName("running")
    val running: JsonObject = buildJsonObject {  },

    @SerialName("runningForm")
    val runningForm: JsonObject = buildJsonObject {  },

    @SerialName("time")
    val time: String = "",

    @SerialName("is_public")
    val isPublic: Boolean = true,

    @SerialName("cul")
    val cul: JsonObject = buildJsonObject {  },

    @SerialName("today_format")
    val todayFormat: String = "",

    @SerialName("eq_date")
    val eqDate: String = "",
)
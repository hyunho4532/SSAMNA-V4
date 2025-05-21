package com.app.domain.model.state

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeSub(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("google_id")
    val googleId: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("description")
    val description: String = "",

    @SerialName("goal")
    val goal: Int? = 0,

    @SerialName("type")
    val type: String? = "",

    @SerialName("today_date")
    val todayDate: String = ""
)
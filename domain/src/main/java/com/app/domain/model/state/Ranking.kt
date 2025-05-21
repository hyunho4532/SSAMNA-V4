package com.app.domain.model.state

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Keep
data class Ranking(
    @SerialName("name")
    val name: String = "",

    @SerialName("sum_km")
    val sumKm: Double = 0.0,

    @SerialName("rank")
    val rank: Int = 0
)
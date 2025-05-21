package com.app.domain.model.state

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeMaster (

    @SerialName("id")
    val id: Int = 0,

    @SerialName("name")
    val name: String = "",

    @SerialName("description")
    val description: String = "",

    @SerialName("assets")
    val assets: String = "",

    @SerialName("type")
    val type: String? = "",

    @SerialName("goal")
    val goal: Int? = 0
)
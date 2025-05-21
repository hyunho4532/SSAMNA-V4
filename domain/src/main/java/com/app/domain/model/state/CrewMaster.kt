package com.app.domain.model.state

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewMaster(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("name")
    val name: String = "",

    @SerialName("description")
    val description: String = "",

    @SerialName("assets")
    val assets: String = "",

    @SerialName("member")
    val member: Int = 0
)
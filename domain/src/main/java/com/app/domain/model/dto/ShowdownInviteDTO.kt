package com.app.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowdownInviteDTO(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("user_id")
    val userId: String = "",

    @SerialName("message")
    val message: String = "",

    @SerialName("other_id")
    val otherId: String = "",

    @SerialName("goal")
    val goal: Int = 0
)
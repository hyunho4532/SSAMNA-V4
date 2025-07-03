package com.app.domain.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShowdownDTO(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("user_id")
    val userId: String = "",

    @SerialName("other_id")
    val otherId: String = "",

    @SerialName("user_name")
    val userName: String = "",

    @SerialName("other_name")
    val otherName: String = "",

    @SerialName("user_steps")
    val userSteps: Int? = 0,

    @SerialName("other_steps")
    val otherSteps: Int? = 0
)
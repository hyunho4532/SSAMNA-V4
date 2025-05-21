package com.app.domain.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Code(
    @SerialName("id")
    val id: Int = 0,

    @SerialName("code")
    val code: String = "",

    @SerialName("name")
    val name: String = "",

    @SerialName("img_path")
    val imgPath: String = ""
)
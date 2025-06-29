package com.app.domain.repository

import com.app.domain.model.state.ActivityType

interface JsonParsingRepository {
    fun jsonParse(jsonFile: String, type: String, onType: (String) -> Unit): List<ActivityType>
    fun dataFromJson(data: String, type: String): List<Any>
}
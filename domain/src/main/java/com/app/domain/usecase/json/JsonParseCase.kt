package com.app.domain.usecase.json

import com.app.domain.model.state.ActivityType
import com.app.domain.repository.JsonParsingRepository
import javax.inject.Inject

class JsonParseCase @Inject constructor(
    private val jsonParsingRepository: JsonParsingRepository
) {
    fun invoke(jsonFile: String, type: String, onType: (String) -> Unit): List<ActivityType> {
        return jsonParsingRepository.jsonParse(jsonFile, type) {
            onType(it)
        }
    }

    fun dataFromJson(data: String, type: String): List<Any> {
        return jsonParsingRepository.dataFromJson(data, type)
    }
}
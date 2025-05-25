package com.app.data.repository.json

import android.content.Context
import android.content.res.AssetManager
import com.app.domain.model.dto.CrewDTO
import com.app.domain.model.location.Coordinate
import com.app.domain.model.state.Activate
import com.app.domain.model.state.ActivateForm
import com.app.domain.model.state.ActivityType
import com.app.domain.repository.JsonParsingRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.utils.io.core.use
import javax.inject.Inject

class JsonParsingRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
): JsonParsingRepository {

    private val gson = Gson()

    override fun jsonParse(jsonFile: String, type: String, onType: (String) -> Unit): List<ActivityType> {

        val listType: TypeToken<*> = when (type) {
            "activate" -> object : TypeToken<List<Activate>>() {}
            else -> object : TypeToken<List<ActivateForm>>() {}
        }

        val assetManager: AssetManager = context.assets

        val json: String = assetManager.open(jsonFile).bufferedReader().use {
            it.readText()
        }

        onType(type)

        return gson.fromJson(json, listType.type)
    }

    override fun dataFromJson(data: String, type: String): List<Any> {
        val listType: TypeToken<*> = when (type) {
            "coordinate" -> object : TypeToken<List<Coordinate>>() {}
            else -> object : TypeToken<List<CrewDTO>>() {}
        }

        return gson.fromJson(data, listType.type)
    }
}
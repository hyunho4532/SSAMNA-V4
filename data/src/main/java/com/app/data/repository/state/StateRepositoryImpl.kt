package com.app.data.repository.state

import com.app.domain.model.dto.ThemeDTO
import com.app.domain.repository.StateRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import javax.inject.Inject

class StateRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): StateRepository {
    override suspend fun themeInsert(userId: String, isTheme: Boolean) {
        return withContext(Dispatchers.IO) {
            val params = buildJsonObject {
                put("p_user_id", JsonPrimitive(userId))
                put("p_is_theme", JsonPrimitive(isTheme))
            }

            postgrest.rpc("update_theme", params)
        }
    }

    override suspend fun themeSelect(userId: String): List<ThemeDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Theme").select {
                filter {
                    eq("user_id", userId)
                }
            }.decodeList<ThemeDTO>()
        }
    }
}
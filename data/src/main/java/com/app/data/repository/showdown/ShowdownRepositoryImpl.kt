package com.app.data.repository.showdown

import com.app.domain.model.dto.ShowdownInviteDTO
import com.app.domain.repository.ShowdownRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import javax.inject.Inject

class ShowdownRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): ShowdownRepository {
    override suspend fun insert(showdownInviteDTO: ShowdownInviteDTO) {
        return withContext(Dispatchers.IO) {
            postgrest.from("ShowdownInvite").insert(showdownInviteDTO)
        }
    }

    override suspend fun select(userId: String): List<ShowdownInviteDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("ShowdownInvite").select {
                filter {
                    eq("user_id", userId)
                }
            }.decodeList<ShowdownInviteDTO>()
        }
    }

    override suspend fun delete(id: Int, onSuccess: (Boolean) -> Unit) {
        return withContext(Dispatchers.IO) {
            val params = buildJsonObject {
                put("pk_id", JsonPrimitive(id))
            }

            postgrest.rpc("delete_showdown_invite", params)
            onSuccess(true)
        }
    }
}
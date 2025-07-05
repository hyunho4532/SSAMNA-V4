package com.app.data.repository.showdown

import com.app.domain.model.calcul.FormatImpl
import com.app.domain.model.dto.ShowdownDTO
import com.app.domain.model.dto.ShowdownInviteDTO
import com.app.domain.repository.ShowdownRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import java.math.BigDecimal
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

    override suspend fun showdownSelect(userId: String) : List<ShowdownDTO> {
        return withContext(Dispatchers.IO) {
            val params = buildJsonObject {
                put("p_user_id", JsonPrimitive(userId))
            }

            postgrest.rpc("get_showdown", params).decodeList<ShowdownDTO>()
        }
    }

    override suspend fun delete(showdownInviteDTO: ShowdownInviteDTO, user: String, other: String, onSuccess: (Boolean) -> Unit) {
        val data = buildJsonArray {
            add(
                buildJsonObject {
                    put("user_id", showdownInviteDTO.userId)
                    put("user_name", user)
                }
            )
            add(
                buildJsonObject {
                    put("user_id", showdownInviteDTO.otherId)
                    put("user_name", other)
                }
            )
        }

        return withContext(Dispatchers.IO) {
            val params = buildJsonObject {
                put("pk_id", JsonPrimitive(showdownInviteDTO.id))
            }

            val showdownDTO = ShowdownDTO(
                userId = showdownInviteDTO.userId,
                otherId = showdownInviteDTO.otherId,
                names = data
            )

            postgrest.rpc("delete_showdown_invite", params)
            postgrest.from("Showdown").insert(showdownDTO)

            onSuccess(true)
        }
    }
}
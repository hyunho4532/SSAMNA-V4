package com.app.data.repository.crew

import com.app.domain.model.dto.ActivateNotificationDTO
import com.app.domain.model.dto.CrewDTO
import com.app.domain.model.state.CrewMaster
import com.app.domain.model.state.Ranking
import com.app.domain.repository.crew.CrewRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import java.lang.NumberFormatException
import javax.inject.Inject

class CrewRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): CrewRepository {
    override suspend fun insert(crewDTO: CrewDTO) {
        return withContext(Dispatchers.IO) {
            val params = buildJsonObject {
                put("p_user_id", JsonPrimitive(crewDTO.userId))
                put("p_title", JsonPrimitive(crewDTO.title))
                put("p_picture", JsonPrimitive(crewDTO.picture))
                put("p_created_at", JsonPrimitive(crewDTO.createdAt))
                put("p_crew_id", JsonPrimitive(crewDTO.crewId))
            }

            postgrest.rpc("insert_crew", params)
        }
    }

    override suspend fun delete(crewId: Int, googleId: String, onResult: (Boolean) -> Unit) {
        return withContext(Dispatchers.IO) {
            val params = buildJsonObject {
                put("crewid", JsonPrimitive(crewId))
                put("userid", JsonPrimitive(googleId))
            }

            val response = postgrest.rpc("delete_crew", params)

            onResult(response.data.toBoolean())
        }
    }

    override suspend fun isCrewDataExists(googleId: String): List<CrewDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("CrewSub").select {
                filter {
                    eq("user_id ", googleId)
                }
            }.decodeList<CrewDTO>()
        }
    }

    override suspend fun crewMasterAll(): List<CrewMaster> {
        return withContext(Dispatchers.IO) {
            postgrest.from("CrewMaster").select().decodeList<CrewMaster>()
        }
    }

    override suspend fun crewFindById(googleId: String): List<CrewDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("CrewSub").select {
                filter {
                    eq("user_id ", googleId)
                }
            }.decodeList<CrewDTO>()
        }
    }

    override suspend fun notificationAll(): List<ActivateNotificationDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("ActivateNotification")
                .select {
                    filter {
                        eq("is_public", true)
                    }
                }
                .decodeList<ActivateNotificationDTO>()
        }
    }

    override suspend fun crewCount(crewId: Int): Int {
        return withContext(Dispatchers.IO) {
            val response = postgrest
                .rpc("get_crew_count", mapOf("crewid" to crewId))

            // response.data가 Int가 아닐 경우 처리
            response.data.toInt()
        }
    }

    override suspend fun crewSumFeed(crewId: Int): Int {
        return withContext(Dispatchers.IO) {
            val response = postgrest
                .rpc("get_sum_feed", mapOf("crewid" to crewId))

            val sumFeed = try {
                response.data.toInt()
            } catch (e: NumberFormatException) {
                0
            }

            sumFeed
        }
    }

    override suspend fun crewRankingTop3(crewId: Int): List<Ranking> {
        return withContext(Dispatchers.IO) {
            postgrest
                .rpc("get_crew_ranking_top3", mapOf("crewid" to crewId))
                .decodeList()
        }
    }
}
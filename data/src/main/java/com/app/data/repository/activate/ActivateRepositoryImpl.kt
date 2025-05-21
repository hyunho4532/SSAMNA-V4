package com.app.data.repository.activate

import android.util.Log
import com.app.domain.model.dto.ActivateDTO
import com.app.domain.model.dto.ActivateNotificationDTO
import com.app.domain.repository.activate.ActivateRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ActivateRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
) : ActivateRepository {
    override suspend fun saveActivity(activateDTO: ActivateDTO, onTime: (Long) -> Unit) {
        onTime(0L)

        postgrest.from("Activate").insert(activateDTO)
    }

    override suspend fun saveActivityNotification(activateNotificationDTO: ActivateNotificationDTO) {
        postgrest.from("ActivateNotification").insert(activateNotificationDTO)
    }

    override suspend fun delete(googleId: String, date: String, onSuccess: (Boolean) -> Unit) {
        postgrest.from("Activate").delete {
            filter {
                eq("google_id", googleId)
                eq("today_format", date)
            }
        }

        onSuccess(true)
    }

    override suspend fun selectActivateByGoogleId(googleId: String): List<ActivateDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Activate").select {
                filter {
                    eq("google_id", googleId)
                }
            }.decodeList<ActivateDTO>()
        }
    }

    override suspend fun selectActivateByDate(googleId: String, date: String): List<ActivateDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Activate").select {
                filter {
                    eq("google_id", googleId)
                    eq("eq_date", date)
                }
            }.decodeList<ActivateDTO>()
        }
    }

    override suspend fun selectActivityFindById(
        id: Int
    ): List<ActivateDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Activate").select {
                filter {
                    eq("id", id)
                }
            }.decodeList<ActivateDTO>()
        }
    }

    override suspend fun selectActivityAll(): List<ActivateDTO> {
        return withContext(Dispatchers.IO) {
            postgrest.from("Activate").select().decodeList()
        }
    }
}
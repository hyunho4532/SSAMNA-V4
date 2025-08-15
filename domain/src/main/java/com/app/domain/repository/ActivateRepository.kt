package com.app.domain.repository

import com.app.domain.model.dto.ActivateDTO
import com.app.domain.model.dto.ActivateNotificationDTO

interface ActivateRepository {
    suspend fun saveActivity(activateDTO: ActivateDTO, time: (Long) -> Unit)
    suspend fun saveActivityNotification(activateNotificationDTO: ActivateNotificationDTO)
    suspend fun delete(googleId: String, date: String, onSuccess: (Boolean) -> Unit)
    suspend fun selectActivateByGoogleId(googleId: String) : List<ActivateDTO>
    suspend fun selectActivateByDate(googleId: String, date: String) : List<ActivateDTO>
    suspend fun selectActivityFindById(id: Int) : List<ActivateDTO>
    suspend fun selectActivityAll(userId: String): List<ActivateDTO>
}
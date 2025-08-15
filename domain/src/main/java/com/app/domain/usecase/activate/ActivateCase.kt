package com.app.domain.usecase.activate

import com.app.domain.model.dto.ActivateDTO
import com.app.domain.model.dto.ActivateNotificationDTO
import com.app.domain.repository.ActivateRepository
import javax.inject.Inject

class ActivateCase @Inject constructor(
    private val activateRepository: ActivateRepository
) {
    suspend fun saveActivity(activateDTO: ActivateDTO, onTime: (Long) -> Unit) {
        activateRepository.saveActivity(activateDTO) {
            onTime(it)
        }
    }

    suspend fun saveActivityNotification(activateNotificationDTO: ActivateNotificationDTO) {
        activateRepository.saveActivityNotification(activateNotificationDTO)
    }

    suspend fun deleteActivity(googleId: String, date: String, onSuccess: (Boolean) -> Unit) {
        activateRepository.delete(googleId, date) {
            onSuccess(it)
        }
    }

    suspend fun selectActivityFindByGoogleId(googleId: String) : List<ActivateDTO> {
        return activateRepository.selectActivateByGoogleId(googleId)
    }

    suspend fun selectActivityFindByDate(googleId: String, date: String) : List<ActivateDTO> {
        return activateRepository.selectActivateByDate(googleId, date)
    }

    suspend fun selectActivityFindById(id: Int) : List<ActivateDTO> {
        return activateRepository.selectActivityFindById(id)
    }

    suspend fun selectActivityAll(userId: String): List<ActivateDTO> {
        return activateRepository.selectActivityAll(userId)
    }
}
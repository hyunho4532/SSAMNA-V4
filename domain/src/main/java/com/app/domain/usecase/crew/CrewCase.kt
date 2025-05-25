package com.app.domain.usecase.crew

import com.app.domain.model.dto.ActivateNotificationDTO
import com.app.domain.model.dto.CrewDTO
import com.app.domain.model.state.CrewMaster
import com.app.domain.model.state.Ranking
import com.app.domain.repository.CrewRepository
import javax.inject.Inject

class CrewCase @Inject constructor(
    private val crewRepository: CrewRepository
) {
    suspend fun insert(crewDTO: CrewDTO) {
        crewRepository.insert(crewDTO)
    }

    suspend fun delete(crewId: Int, userId: String, onResult: (Boolean) -> Unit) {
        crewRepository.delete(crewId, userId) {
            onResult(it)
        }
    }

    suspend fun isCrewDataExists(googleId: String): List<CrewDTO> {
        return crewRepository.isCrewDataExists(googleId)
    }

    suspend fun crewMasterAll(): List<CrewMaster> {
        return crewRepository.crewMasterAll()
    }

    suspend fun crewFindById(googleId: String): List<CrewDTO> {
        return crewRepository.crewFindById(googleId)
    }

    suspend fun notificationAll(): List<ActivateNotificationDTO> {
        return crewRepository.notificationAll()
    }

    suspend fun crewCount(crewId: Int): Int {
        return crewRepository.crewCount(crewId)
    }

    suspend fun crewSumFeed(crewId: Int): Int {
        return crewRepository.crewSumFeed(crewId)
    }

    suspend fun crewRankingTop3(crewId: Int): List<Ranking> {
        return crewRepository.crewRankingTop3(crewId)
    }
}
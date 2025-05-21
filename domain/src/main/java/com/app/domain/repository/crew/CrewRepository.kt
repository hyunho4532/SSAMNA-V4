package com.app.domain.repository.crew

import com.app.domain.model.dto.ActivateNotificationDTO
import com.app.domain.model.dto.CrewDTO
import com.app.domain.model.state.CrewMaster
import com.app.domain.model.state.Ranking

interface CrewRepository {
    suspend fun insert(crewDTO: CrewDTO)
    suspend fun delete(crewId: Int, googleId: String, onResult: (Boolean) -> Unit)
    suspend fun isCrewDataExists(googleId: String): List<CrewDTO>
    suspend fun crewMasterAll(): List<CrewMaster>
    suspend fun crewFindById(googleId: String): List<CrewDTO>
    suspend fun notificationAll(): List<ActivateNotificationDTO>
    suspend fun crewCount(crewId: Int): Int
    suspend fun crewSumFeed(crewId: Int): Int
    suspend fun crewRankingTop3(crewId: Int): List<Ranking>
}
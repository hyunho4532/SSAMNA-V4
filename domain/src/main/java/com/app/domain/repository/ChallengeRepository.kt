package com.app.domain.repository

import com.app.domain.model.dto.ChallengeDTO
import com.app.domain.model.state.ChallengeMaster
import com.app.domain.model.state.ChallengeSub


interface ChallengeRepository {
    suspend fun insert(challengeSub: ChallengeSub)

    suspend fun selectChallengeAll() : List<ChallengeMaster>

    suspend fun selectChallengeFindById(id: Int) : List<ChallengeDTO>

    suspend fun selectChallengeFindByGoogleId(googleId: String) : List<ChallengeDTO>

    suspend fun delete(id: Int, onSuccess: (Boolean) -> Unit)
}
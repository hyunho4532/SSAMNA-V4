package com.app.domain.usecase.challenge

import com.app.domain.model.dto.ChallengeDTO
import com.app.domain.model.state.ChallengeMaster
import com.app.domain.model.state.ChallengeSub
import com.app.domain.repository.ChallengeRepository
import javax.inject.Inject

class ChallengeCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend fun saveChallenge(challengeSub: ChallengeSub) {
        challengeRepository.insert(challengeSub)
    }

    suspend fun selectChallengeAll(): List<ChallengeMaster> {
        return challengeRepository.selectChallengeAll()
    }

    suspend fun selectChallengeFindById(id: Int) : List<ChallengeDTO> {
        return challengeRepository.selectChallengeFindById(id)
    }

    suspend fun selectChallengeFindByGoogleId(googleId: String) : List<ChallengeDTO> {
        return challengeRepository.selectChallengeFindByGoogleId(googleId)
    }

    suspend fun deleteChallenge(id: Int, onSuccess: (Boolean) -> Unit) {
        challengeRepository.delete(id) {
            onSuccess(it)
        }
    }
}
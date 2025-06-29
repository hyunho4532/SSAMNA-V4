package com.app.domain.usecase.state

import com.app.domain.model.dto.ThemeDTO
import com.app.domain.repository.StateRepository
import javax.inject.Inject

class StateCase @Inject constructor(
    private val stateRepository: StateRepository
) {
    /**
     * Theme 테이블에 등록
     */
    suspend fun themeInsert(userId: String, isTheme: Boolean) {
        stateRepository.themeInsert(userId, isTheme)
    }

    suspend fun themeSelect(userId: String) : List<ThemeDTO> {
        return stateRepository.themeSelect(userId)
    }
}
package com.app.domain.usecase.showdown

import com.app.domain.model.dto.ShowdownInviteDTO
import com.app.domain.repository.ShowdownRepository
import javax.inject.Inject

class ShowdownCase @Inject constructor(
    private val showdownRepository: ShowdownRepository
) {
    suspend fun insert(showdownInviteDTO: ShowdownInviteDTO) {
        showdownRepository.insert(showdownInviteDTO)
    }

    suspend fun select(userId: String): List<ShowdownInviteDTO> {
        return showdownRepository.select(userId)
    }
}
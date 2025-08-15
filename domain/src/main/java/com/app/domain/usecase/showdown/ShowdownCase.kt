package com.app.domain.usecase.showdown

import com.app.domain.model.dto.ShowdownDTO
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

    suspend fun showdownSelect(userId: String) : List<ShowdownDTO> {
        return showdownRepository.showdownSelect(userId)
    }

    suspend fun delete(showdownInviteDTO: ShowdownInviteDTO, user: String, other: String, onSuccess: (Boolean) -> Unit) {
        showdownRepository.delete(showdownInviteDTO, user, other) {
            onSuccess(it)
        }
    }

    suspend fun showdownDelete(showdownDTO: ShowdownDTO) {
        showdownRepository.showdownDelete(showdownDTO)
    }
}
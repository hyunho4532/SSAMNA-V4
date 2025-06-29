package com.app.domain.repository

import com.app.domain.model.dto.ShowdownInviteDTO

interface ShowdownRepository {
    suspend fun insert(showdownInviteDTO: ShowdownInviteDTO)
    suspend fun select(userId: String) : List<ShowdownInviteDTO>
}
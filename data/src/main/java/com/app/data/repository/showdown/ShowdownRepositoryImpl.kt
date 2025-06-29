package com.app.data.repository.showdown

import com.app.domain.model.dto.ShowdownInviteDTO
import com.app.domain.repository.ShowdownRepository
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShowdownRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): ShowdownRepository {
    override suspend fun insert(showdownInviteDTO: ShowdownInviteDTO) {
        return withContext(Dispatchers.IO) {
            postgrest.from("ShowdownInvite").insert(showdownInviteDTO)
        }
    }
}
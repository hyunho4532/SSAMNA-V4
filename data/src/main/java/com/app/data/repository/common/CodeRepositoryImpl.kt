package com.app.data.repository.common

import com.app.domain.model.common.Code
import com.app.domain.repository.CodeRepository
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.rpc
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CodeRepositoryImpl @Inject constructor(
    private val postgrest: Postgrest
): CodeRepository {
    override suspend fun select(): List<Code> {
        return withContext(Dispatchers.IO) {
            postgrest
                .rpc("get_common_code", mapOf("groupkey" to "ACTIVATE_STATUS"))
                .decodeList<Code>()
        }
    }
}
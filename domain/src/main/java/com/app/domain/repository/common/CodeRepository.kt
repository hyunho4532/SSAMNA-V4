package com.app.domain.repository.common

import com.app.domain.model.common.Code

interface CodeRepository {
    suspend fun select(): List<Code>
}
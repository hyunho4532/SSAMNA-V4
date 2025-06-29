package com.app.domain.repository

import com.app.domain.model.common.Code

interface CodeRepository {
    suspend fun select(): List<Code>
}
package com.app.domain.repository

import com.app.domain.model.dto.ThemeDTO

interface StateRepository {
    suspend fun themeInsert(userId: String, isTheme: Boolean)
    suspend fun themeSelect(userId: String): List<ThemeDTO>
}
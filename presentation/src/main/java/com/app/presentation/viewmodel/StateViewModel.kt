package com.app.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.dto.ThemeDTO
import com.app.domain.usecase.state.StateCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 앱의 상태를 관리한다.
 */
@HiltViewModel
class StateViewModel @Inject constructor(
    private val stateCase: StateCase,
    @ApplicationContext appContext: Context?
) : ViewModel() {

    private val sharedPreferences = appContext?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    /**
     * 테마를 관리한다.
     */
    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    fun toggleTheme() {
        val userId = sharedPreferences?.getString("id", "")
        _isDarkTheme.value = !_isDarkTheme.value

        viewModelScope.launch {
            stateCase.themeInsert(
                userId = userId!!,
                isTheme = isDarkTheme.value
            )
        }
    }

    /**
     * Theme 테이블에 내가 등록한 is_theme 조회한다.
     */
    suspend fun themeSelect() {
        val userId = sharedPreferences?.getString("id", "")

        val theme = stateCase.themeSelect(
            userId = userId!!
        ).firstOrNull()

        _isDarkTheme.value = theme?.isTheme ?: false
    }
}
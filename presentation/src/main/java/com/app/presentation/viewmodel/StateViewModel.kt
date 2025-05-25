package com.app.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * 앱의 상태를 관리한다.
 */
@HiltViewModel
class StateViewModel @Inject constructor(
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
        Log.d("StateViewModel", "userId: $userId, isTheme: ${_isDarkTheme.value}")

        _isDarkTheme.value = !_isDarkTheme.value
    }
}
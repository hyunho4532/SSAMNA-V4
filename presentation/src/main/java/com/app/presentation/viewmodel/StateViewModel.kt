package com.app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 앱의 상태를 관리한다.
 */
@HiltViewModel
class StateViewModel @Inject constructor(
) : ViewModel() {
    /**
     * 테마를 관리한다.
     */
    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    fun toggleTheme() {
        Log.d("StateViewModel", "1234")
        _isDarkTheme.value = !_isDarkTheme.value
    }
}
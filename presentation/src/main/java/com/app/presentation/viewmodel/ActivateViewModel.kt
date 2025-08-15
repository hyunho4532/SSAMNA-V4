package com.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.app.domain.model.dto.ActivateDTO
import com.app.domain.usecase.activate.ActivateCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivateViewModel @Inject constructor(
    private val activateCase: ActivateCase
): ViewModel() {
    suspend fun select(userId: String): List<ActivateDTO> {
        return activateCase.selectActivityAll(userId)
    }
}
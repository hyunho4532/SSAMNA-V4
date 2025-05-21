package com.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.app.domain.model.common.Code
import com.app.domain.usecase.common.CodeCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommonCodeViewModel @Inject constructor(
    private val codeCase: CodeCase
): ViewModel() {
    /**
     * 공통 코드 조회
     */
    suspend fun select(): List<Code> {
        return codeCase.select()
    }
}

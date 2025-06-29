package com.app.domain.usecase.common

import com.app.domain.model.common.Code
import com.app.domain.repository.CodeRepository
import javax.inject.Inject

class CodeCase @Inject constructor(
    private val codeRepository: CodeRepository
) {
    suspend fun select(): List<Code> {
        return codeRepository.select()
    }
}
package com.app.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.domain.model.dto.ShowdownInviteDTO
import com.app.domain.model.user.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ShowdownViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    fun insert(
        userId: String,
        username: String,
        data: UserDTO
    ) {
        Log.d("ShowdownViewModel", data.toString())

        val showdownInviteDTO = ShowdownInviteDTO(
            userId = userId,
            otherId = data.userId,
            message = "${username}님이 대결을 신청했습니다!"
        )

        Log.d("ShowdownViewModel", showdownInviteDTO.toString())
    }
}
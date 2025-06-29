package com.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.dto.ShowdownInviteDTO
import com.app.domain.model.user.UserDTO
import com.app.domain.usecase.showdown.ShowdownCase
import com.app.domain.usecase.user.LoginCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowdownViewModel @Inject constructor(
    private val loginCase: LoginCase,
    private val showdownCase: ShowdownCase
) : ViewModel() {
    fun insert(
        userId: String,
        username: String,
        data: UserDTO
    ) {
        viewModelScope.launch {
            val user = loginCase.selectUserFindById(userId)

            val showdownInviteDTO = ShowdownInviteDTO(
                userId = data.userId,
                otherId = userId,
                message = "${user.name}님이 대결을 신청했습니다!"
            )

            showdownCase.insert(showdownInviteDTO)
        }
    }

    suspend fun select(
        userId: String
    ) : List<ShowdownInviteDTO> {
        return showdownCase.select(userId)
    }
}
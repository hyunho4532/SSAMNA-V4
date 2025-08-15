package com.app.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.domain.model.dto.ShowdownDTO
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
    private val _inviteList = mutableStateListOf<ShowdownInviteDTO>()
    val inviteList: List<ShowdownInviteDTO> get() = _inviteList
    
    fun insert(
        userId: String,
        username: String,
        data: UserDTO,
        subData: Int
    ) {
        viewModelScope.launch {
            val user = loginCase.selectUserFindById(userId)

            val showdownInviteDTO = ShowdownInviteDTO(
                userId = data.userId,
                otherId = userId,
                message = "${user.name}님이 대결을 신청했습니다!",
                goal = subData
            )

            showdownCase.insert(showdownInviteDTO)
        }
    }

    suspend fun select(
        userId: String
    ) : List<ShowdownInviteDTO> {
        return showdownCase.select(userId)
    }

    suspend fun showdownSelect(
        userId: String
    ) : List<ShowdownDTO> {
        return showdownCase.showdownSelect(userId)
    }

    fun delete(
        showdownInviteDTO: ShowdownInviteDTO,
        onSuccess: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            /**
             * user: 현재 사용자
             * other: 현재 사용자한테 대결 승인을 보낸 사용자 (대결자)
             */
            val user = loginCase.selectName(showdownInviteDTO.userId)
            val other = loginCase.selectName(showdownInviteDTO.otherId)

            showdownCase.delete(showdownInviteDTO, user, other) {
                onSuccess(it)
            }
        }
    }

    fun showdownDelete(
        showdownDTO: ShowdownDTO
    ) {
        viewModelScope.launch {
            showdownCase.showdownDelete(showdownDTO)
        }
    }
}
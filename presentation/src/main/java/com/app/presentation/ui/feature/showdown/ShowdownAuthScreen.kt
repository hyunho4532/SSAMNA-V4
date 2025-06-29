package com.app.presentation.ui.feature.showdown

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.state.ChallengeMaster
import com.app.domain.model.user.UserDTO
import com.app.presentation.viewmodel.UserViewModel

/**
 * 대결 하기 전, 사용자를 먼저 선택 후, 대결을 시작해야 한다.
 */
@Composable
fun ShowdownAuthScreen(
    userViewModel: UserViewModel = hiltViewModel()
) {
    val userListMaster = remember {
        mutableStateListOf<UserDTO>()
    }

    LaunchedEffect(key1 = Unit) {
        val userList = userViewModel.selectUserAll()
        userListMaster.addAll(userList)
    }

    Column(
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp)
    ) {
        Text(
            text = "대결할 상대를 선택해주세요!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "누가 더 잘 달릴까요?",
            fontSize = 14.sp,
        )

        userListMaster.forEach { userDTO ->
            Text(
                text = userDTO.name
            )
        }
    }
}
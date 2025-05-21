package com.app.presentation.ui.feature.login

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.presentation.R
import com.app.presentation.api.GoogleApiContract
import com.app.presentation.component.tool.CustomCard
import com.app.presentation.component.tool.Spacer
import com.app.presentation.component.util.responsive.setTopPadding
import com.app.presentation.viewmodel.UserViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun LoginScreen(
    viewModel: UserViewModel = hiltViewModel(),
    navController: NavController
) {
    val authState by viewModel.user.collectAsState()

    var isNotUser by remember {
        mutableStateOf(false)
    }

    val authResultLauncher = rememberLauncherForActivityResult (
        contract = GoogleApiContract()
    ) { task ->
        viewModel.saveLoginState(task!!.result.id.toString(), task.result.email.toString())

        viewModel.onGoogleSignIn(task) {
            isNotUser = it
        }
    }

    LaunchedEffect(key1 = authState.email) {
        if (authState.email.isNotEmpty() && isNotUser) {
            val authStateJson = Uri.encode(Json.encodeToString(authState))
            navController.navigate("userInfo?authState=${authStateJson}")
        }
    }

    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = 1f
        )
    ) {
        BoxWithConstraints (
            modifier = Modifier
                .fillMaxSize()
        ) {

            val screenWidth = maxWidth
            val screenHeight = maxHeight

            val topPadding = setTopPadding(screenWidth)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = topPadding,
                        start = 8.dp
                    )
            ) {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.50f)
                ) {
                    Text(
                        text = "나 자신을 관리하자.",
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "땀나(SSAMNA) 💦",
                        modifier = Modifier.padding(start = 16.dp, top = 34.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "환영합니다! 같이 떠나볼까요?",
                        modifier = Modifier.padding(start = 16.dp, top = 66.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Box (
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            authResultLauncher.launch(1)
                        }
                ) {
                    CustomCard(
                        width = screenWidth * 0.8f,
                        height = 52.dp,
                        text = "구글 계정으로 로그인",
                        id = R.drawable.google
                    )
                }

                Spacer(width = 0.dp, height = 16.dp)

                Box (
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    CustomCard(
                        width = screenWidth * 0.8f,
                        height = 52.dp,
                        text = "카카오 계정으로 로그인",
                        id = R.drawable.kakao
                    )
                }
            }
        }
    }
}
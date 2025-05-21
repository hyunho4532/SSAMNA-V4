package com.app.presentation.ui.feature.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.domain.model.user.User
import com.app.presentation.component.tool.ReportCard
import com.app.presentation.ui.main.home.HomeActivity
import com.app.presentation.component.util.responsive.setUpButtonWidth
import com.app.presentation.component.util.getDPI
import com.app.presentation.viewmodel.UserViewModel

/** 정보 수집 후 사용자에 관한 최종 정보 **/
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ReportScreen(
    userState: User,
    userViewModel: UserViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    val densityDpi = getDPI(context)

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
            val buttonWidth = setUpButtonWidth(densityDpi)

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                ) {
                    Text(
                        text = "회원님의 정보를 수집했어요!",
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "정보를 수정하고 싶으시면 뒤로 이동해주세요!",
                        modifier = Modifier.padding(top = 48.dp, start = 16.dp),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    ReportCard(
                        userState = userState
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Button(
                        onClick = {
                            userViewModel.saveUser(userState)

                            val intent = Intent(context, HomeActivity::class.java)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .width(buttonWidth)
                            .padding(16.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF5c9afa)
                        )
                    ) {
                        Text(text = "작성 확인!")
                    }
                }

            }
        }
    }
}
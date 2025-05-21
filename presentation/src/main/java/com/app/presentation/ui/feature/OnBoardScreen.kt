package com.app.presentation.ui.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.presentation.R
import com.app.presentation.animation.SplashLoader
import com.app.presentation.component.dialog.PermissionDialog
import com.app.presentation.component.tool.CustomButton
import com.app.presentation.component.tool.Spacer
import com.app.domain.model.enum.ButtonType
import com.app.presentation.component.dialog.PrivacyConsentDialog
import com.app.presentation.component.util.responsive.setSubTitleFontSize
import com.app.presentation.component.util.responsive.setTitleFontSize
import com.app.presentation.component.util.responsive.setContentPadding

@Composable
fun OnBoardingScreen(
    navController: NavController
) {

    /**
     * 사용자 권한 확인 팝업
     */
    val isPermissionPopup = remember {
        mutableStateOf(false)
    }

    /**
     * 개인 정보 수집 동의 권한 확인 팝업
     */
    val isPrivacyPopup = remember {
        mutableStateOf(false)
    }

    /**
     * 사용자 권한 확인 상태
     */
    val isUserPermissionCheck = remember {
        mutableStateOf(false)
    }

    /**
     * 개인 정보 수집 권한 확인 상태
     */
    val isPrivacyPermissionCheck = remember {
        mutableStateOf(false)
    }

    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = 1f
        )
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val screenWidth = maxWidth
            val screenHeight = maxHeight

            val titleFontSize = setTitleFontSize(screenWidth)
            val subtitleFontSize = setSubTitleFontSize(screenWidth)
            val contentPadding = setContentPadding(screenWidth)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = contentPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.35f)
                ) {
                    SplashLoader(R.raw.init)
                }

                Text(
                    text = "운동할 땐 땀💦 배출하자!",
                    fontSize = titleFontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = contentPadding)
                )

                Text(
                    text = "언제 어디서든 편하게!",
                    fontSize = subtitleFontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = contentPadding)
                )

                Text(
                    text = "운동을 즐기세요",
                    fontSize = subtitleFontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = contentPadding / 2)
                )

                Spacer(width = 0.dp, height = 60.dp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .width(screenWidth * 0.8f)
                                .height(40.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    isPermissionPopup.value = true
                                }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(start = 6.dp),
                                    text = "사용자 권한 확인하기",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                )

                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                                        contentDescription = "화살표",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )

                                    Checkbox(
                                        checked = isUserPermissionCheck.value,
                                        onCheckedChange = {

                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(width = 0.dp, height = 20.dp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Card(
                            modifier = Modifier
                                .width(screenWidth * 0.8f)
                                .height(40.dp)
                                .align(Alignment.CenterVertically)
                                .clickable {
                                    isPrivacyPopup.value = true
                                }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(start = 6.dp),
                                    text = "개인정보 수집 동의",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )

                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                                        contentDescription = "화살표",
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )

                                    Checkbox(
                                        checked = isPrivacyPermissionCheck.value,
                                        onCheckedChange = {
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(width = 0.dp, height = 20.dp)

                if (isUserPermissionCheck.value && isPrivacyPermissionCheck.value) {
                    CustomButton(
                        type = ButtonType.EventStatus.ROUTE,
                        width = screenWidth * 0.8f,
                        height = 46.dp,
                        text = "운동 여정하기!",
                        showIcon = true,
                        shape = "Rectangle",
                        onClick = {
                            if (it) {
                                navController.navigate("login")
                            }
                        }
                    )
                }
            }
        }

        if (isPermissionPopup.value) {
            PermissionDialog(
                isPermissionPopup = isPermissionPopup,
                onPermissionUserCheck = {
                    isUserPermissionCheck.value = it
                }
            )
        }

        if (isPrivacyPopup.value) {
            PrivacyConsentDialog(
                isPrivacyPermissionPopup = isPrivacyPopup,
                onPermissionPrivacyCheck = {
                    isPrivacyPermissionCheck.value = it
                }
            )
        }
    }
}
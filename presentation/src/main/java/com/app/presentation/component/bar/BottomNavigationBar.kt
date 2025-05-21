package com.app.presentation.component.bar

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.PratikFagadiya.smoothanimationbottombar.model.SmoothAnimationBottomBarScreens
import com.PratikFagadiya.smoothanimationbottombar.properties.BottomBarProperties
import com.PratikFagadiya.smoothanimationbottombar.ui.SmoothAnimationBottomBar
import com.app.presentation.viewmodel.StateViewModel

@Composable
fun BottomNavigationBar(
    items: List<SmoothAnimationBottomBarScreens>,
    currentIndex: MutableIntState,
    navController: NavHostController,
    stateViewModel: StateViewModel
) {
    val backgroundColor = if (stateViewModel.isDarkTheme.value) {
        Color.Gray
    } else {
        Color.White
    }

    val iconTintColor = if (stateViewModel.isDarkTheme.value) {
        Color.White
    } else {
        Color.Gray
    }

    val iconTintActiveColor = if (stateViewModel.isDarkTheme.value) {
        Color.White
    } else {
        Color.Black
    }

    val textActiveColor = if (stateViewModel.isDarkTheme.value) {
        Color.White
    } else {
        Color.Black
    }

    SmoothAnimationBottomBar (
        navController = navController,
        bottomNavigationItems = items,
        initialIndex = currentIndex,
        bottomBarProperties = BottomBarProperties(
            backgroundColor = backgroundColor,
            indicatorColor = Color.White.copy(alpha = 0.5F),
            iconTintColor = iconTintColor,
            iconTintActiveColor = iconTintActiveColor,
            textActiveColor = textActiveColor,
            cornerRadius = 18.dp,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        ),
        onSelectItem = {

        },
    )
}
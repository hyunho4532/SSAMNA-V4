package com.app.presentation.ui.main.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.presentation.component.theme.SsamnaTheme
import com.app.presentation.viewmodel.StateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themeViewModel: StateViewModel = viewModel()

            LaunchedEffect(Unit) {
                themeViewModel.themeSelect()
            }

            val isDarkTheme by themeViewModel.isDarkTheme

            SsamnaTheme(darkTheme = isDarkTheme) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    CompositionLocalProvider(
                        value = LocalDensity provides Density(
                            density = LocalDensity.current.density,
                            fontScale = 1f
                        ),
                    ) {
                        RootScreen(
                            stateViewModel = themeViewModel
                        )
                    }
                }
            }
        }
    }
}
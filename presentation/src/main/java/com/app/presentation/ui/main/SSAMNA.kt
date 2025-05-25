package com.app.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.app.presentation.route.AppNavHost
import com.app.presentation.ui.main.home.HomeActivity
import com.app.presentation.viewmodel.UserViewModel
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SSAMNA : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * MobileAds 초기화
         */
        MobileAds.initialize(this) {}

        val id = userViewModel.user.value.id

        setContent {
            if (id != "") {
                navigateToHome()
            } else {
                AppNavHost()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}

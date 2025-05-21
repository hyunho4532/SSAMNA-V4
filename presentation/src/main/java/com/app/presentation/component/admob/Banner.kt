package com.app.presentation.component.admob

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun Banner() {
    AndroidView(factory = { context ->
        AdView(context).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = "ca-app-pub-3025693686135055/6799937946"
            loadAd(AdRequest.Builder().build())
        }
    })
}
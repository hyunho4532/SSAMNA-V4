package com.app.presentation.component.box.polygon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import com.app.presentation.viewmodel.StateViewModel

/**
 * 다각형 박스
 */
@Composable
fun <T> PolygonBox(
    width: Dp = 90.dp,
    height: Dp = 90.dp,
    title: String,
    data: T,
    stateViewModel: StateViewModel = StateViewModel()
) {
    val hexagon = remember {
        RoundedPolygon(
            6,
            rounding = CornerRounding(0.2f)
        )
    }

    val clip = remember(hexagon) {
        RoundedPolygonShape(polygon = hexagon)
    }

    val background = if (stateViewModel.isDarkTheme.value) {
        Color(0xFF606060)
    } else {
        Color(0xFF429bf5)
    }

    val textColor = if (stateViewModel.isDarkTheme.value) {
        Color.LightGray
    } else {
        Color.White
    }

    Box(
        modifier = Modifier
            .clip(clip)
            .background(background)
            .width(width)
            .height(height)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                title,
                color = textColor
            )

            Text(
                text = if (data is Double) { String.format("%.2f", data) } else { data.toString() },
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
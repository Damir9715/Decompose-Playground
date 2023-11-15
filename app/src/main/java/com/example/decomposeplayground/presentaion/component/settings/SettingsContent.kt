package com.example.decomposeplayground.presentaion.component.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun SettingsContent(
        component: SettingsComponent,
        modifier: Modifier,
) {

    Box(
            modifier = modifier.background(Color.Gray),
            contentAlignment = Alignment.Center
    ) {
        Text(text = "Settings", fontSize = 50.sp)
    }
}
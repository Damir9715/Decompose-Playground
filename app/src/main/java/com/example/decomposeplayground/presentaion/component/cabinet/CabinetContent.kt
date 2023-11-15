package com.example.decomposeplayground.presentaion.component.cabinet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.decomposeplayground.presentaion.component.cabinet.CabinetComponent

@Composable
fun CabinetContent(
        component: CabinetComponent,
        modifier: Modifier,
) {
    Box(
            modifier = modifier.background(Color.Gray),
            contentAlignment = Alignment.Center
    ) {
        Text(text = "Cabinet", fontSize = 50.sp)
    }
}

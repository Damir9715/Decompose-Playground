package com.example.decomposeplayground.presentaion.component.cabinet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CabinetContent(
        component: CabinetComponent,
        modifier: Modifier,
) {

    Column(
            modifier = modifier.background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Cabinet", fontSize = 50.sp)
        Spacer(modifier = Modifier.padding(40.dp))
        Button(onClick = { component.onSettingsClicked() }) {
            Text(text = "Open Settings", fontSize = 20.sp)
        }
    }
}
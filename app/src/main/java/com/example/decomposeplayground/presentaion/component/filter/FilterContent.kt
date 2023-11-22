package com.example.decomposeplayground.presentaion.component.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun FilterContent(
        component: FilterComponent,
        modifier: Modifier,
) {
    val state by component.viewModel.state.subscribeAsState()

    Column(
            modifier = modifier.background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Filter - sqb: ${state.sqb}", fontSize = 50.sp)
        Spacer(modifier = Modifier.padding(40.dp))
        Button(onClick = { component.onIncrementSqbClicked() }) {
            Text(text = "Increment sqb", fontSize = 50.sp)
        }
        Spacer(modifier = Modifier.padding(40.dp))
        Button(onClick = { component.onFilterApplied() }) {
            Text(text = "Apply filter", fontSize = 50.sp)
        }
    }
}
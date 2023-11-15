package com.example.decomposeplayground.presentaion.component.advertdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState

@Composable
fun AdvertDetailsContent(
        component: AdvertDetailsComponent,
        modifier: Modifier,
) {
    val advertDetails by component.state.subscribeAsState()

    Column(modifier = modifier) {
        TopAppBar(
                title = { Text(advertDetails.title) },
                navigationIcon = {
                    IconButton(onClick = component::onCloseClicked) {
                        Icon(Icons.Default.ArrowBack, "ArrowBack")
                    }
                }
        )
        Text(
                text = advertDetails.text,
                modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
        )
    }
}
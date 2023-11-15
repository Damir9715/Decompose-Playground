package com.example.decomposeplayground.presentaion.component.advertlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
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
fun AdvertListContent(
        component: AdvertListComponent,
        modifier: Modifier,
) {
    val state: AdvertListComponent.State by component.state.subscribeAsState()

    Column(modifier = modifier) {
        //Toolbar
        Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "AdvertList", fontSize = 24.sp, modifier = Modifier.padding(start = 16.dp))
            Button(modifier = Modifier.padding(2.dp), onClick = { }) {
                Text(text = "Filter", fontSize = 24.sp)
            }
        }
        Divider()

        //Listing
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(state.adverts) { adverts ->
                val isSelected = adverts.id == state.selectedAdvertId
                Text(
                        text = adverts.title,
                        modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                        selected = isSelected,
                                        onClick = { component.onAdvertClicked(id = adverts.id) }
                                )
                                .run { if (isSelected) background(color = selectionColor()) else this }
                                .padding(16.dp)
                )
                Divider()
            }
        }
    }
}

@Composable
private fun selectionColor(): Color =
        MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
package com.example.decomposeplayground.presentaion.component.advertdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
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
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.decomposeplayground.presentaion.component.ordercall.OrderCallDialogContent

@Composable
fun AdvertDetailsContent(
        component: AdvertDetailsComponent,
        modifier: Modifier,
) {
    val state by component.state.subscribeAsState()
    val dialogSlot by component.dialogSlot.subscribeAsState()

    dialogSlot.child?.instance?.also {
        OrderCallDialogContent(dialogComponent = it)
    }

    Column(modifier = modifier) {
        TopAppBar(
                title = { Text(state.advertDetails.title) },
                navigationIcon = {
                    IconButton(onClick = component::onCloseClicked) {
                        Icon(Icons.Default.ArrowBack, "ArrowBack")
                    }
                }
        )
        Text(
                text = state.advertDetails.text,
                modifier = Modifier
                        .weight(1F)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
        )
        Button(
                onClick = { component.onOrderCallClicked() },
                modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
        ) {
            Text(text = "Позвонить", fontSize = 20.sp)
        }
    }
}
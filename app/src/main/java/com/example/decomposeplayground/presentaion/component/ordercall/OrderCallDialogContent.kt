package com.example.decomposeplayground.presentaion.component.ordercall

import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrderCallDialogContent(dialogComponent: OrderCallDialogComponent) {
    AlertDialog(
            onDismissRequest = {
                dialogComponent.onDismissed()
            },
            title = {
                Text(text = "Order call dialog")
            },
            confirmButton = {
                TextButton(onClick = { dialogComponent.onDismissed() }) {
                    Text("Close")
                }
            },
            modifier = Modifier.width(300.dp),
    )
}
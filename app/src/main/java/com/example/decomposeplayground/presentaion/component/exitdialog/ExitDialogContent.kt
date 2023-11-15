package com.example.decomposeplayground.presentaion.component.exitdialog

import androidx.compose.foundation.layout.width
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExitDialogContent(dialogComponent: ExitDialogComponent) {
    AlertDialog(
            onDismissRequest = {
                dialogComponent.onDismissed()
            },
            title = {
                Text(text = "Вы действительно хотите закрыть приложение?")
            },
            confirmButton = {
                TextButton(onClick = { dialogComponent.onConfirmed() }) {
                    Text("Да")
                }
            },
            dismissButton = {
                TextButton(onClick = { dialogComponent.onDismissed() }) {
                    Text("Нет")
                }
            },
            modifier = Modifier.width(300.dp),
    )
}
package com.example.decomposeplayground.presentaion.component.exitdialog

interface ExitDialogComponent {

    fun onDismissed()

    fun onConfirmed()
}

class ExitDialogComponentImpl(
        private val onDismissed: () -> Unit,
        private val onConfirmed: () -> Unit,
) : ExitDialogComponent {

    override fun onDismissed() {
        onDismissed.invoke()
    }

    override fun onConfirmed() {
        onConfirmed.invoke()
    }
}